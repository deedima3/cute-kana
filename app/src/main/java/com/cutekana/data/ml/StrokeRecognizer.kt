package com.cutekana.data.ml

import android.content.Context
import com.cutekana.data.model.PointF
import com.cutekana.data.model.StrokeData
import com.cutekana.data.model.KanaStrokeData
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import java.nio.MappedByteBuffer
import javax.inject.Inject
import javax.inject.Singleton

/**
 * TensorFlow Lite-based stroke recognition system
 * Recognizes handwritten kana and kanji with confidence scores
 */
@Singleton
class StrokeRecognizer @Inject constructor(
    private val context: Context
) {
    private var interpreter: Interpreter? = null
    private val inputSize = 64 // 64x64 normalized input
    private val numClasses = 342 // 92 kana + 250 kanji
    
    // Character index mapping
    private val characterIndexMap = mutableMapOf<String, Int>()
    private val indexCharacterMap = mutableMapOf<Int, String>()
    
    init {
        initializeModel()
        initializeCharacterMapping()
    }
    
    private fun initializeModel() {
        try {
            val model: MappedByteBuffer = FileUtil.loadMappedFile(
                context,
                "stroke_recognition_model.tflite"
            )
            interpreter = Interpreter(model)
        } catch (e: Exception) {
            // Fallback to heuristic recognition if model not available
            e.printStackTrace()
        }
    }
    
    private fun initializeCharacterMapping() {
        // Initialize hiragana (0-45)
        val hiragana = listOf(
            "あ", "い", "う", "え", "お",
            "か", "き", "く", "け", "こ",
            "さ", "し", "す", "せ", "そ",
            "た", "ち", "つ", "て", "と",
            "な", "に", "ぬ", "ね", "の",
            "は", "ひ", "ふ", "へ", "ほ",
            "ま", "み", "む", "め", "も",
            "や", "ゆ", "よ",
            "ら", "り", "る", "れ", "ろ",
            "わ", "を", "ん"
        )
        
        // Initialize katakana (46-91)
        val katakana = listOf(
            "ア", "イ", "ウ", "エ", "オ",
            "カ", "キ", "ク", "ケ", "コ",
            "サ", "シ", "ス", "セ", "ソ",
            "タ", "チ", "ツ", "テ", "ト",
            "ナ", "ニ", "ヌ", "ネ", "ノ",
            "ハ", "ヒ", "フ", "ヘ", "ホ",
            "マ", "ミ", "ム", "メ", "モ",
            "ヤ", "ユ", "ヨ",
            "ラ", "リ", "ル", "レ", "ロ",
            "ワ", "ヲ", "ン"
        )
        
        // Map all kana
        hiragana.forEachIndexed { index, char ->
            characterIndexMap[char] = index
            indexCharacterMap[index] = char
        }
        
        katakana.forEachIndexed { index, char ->
            val globalIndex = 46 + index
            characterIndexMap[char] = globalIndex
            indexCharacterMap[globalIndex] = char
        }
        
        // Kanji indices (92-341) will be loaded from JSON asset
        loadKanjiMapping()
    }
    
    private fun loadKanjiMapping() {
        try {
            val json = context.assets.open("kanji/character_mapping.json")
                .bufferedReader()
                .use { it.readText() }
            // Parse and add to maps
            // Format: {"kanji": "日", "index": 92}
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * Recognize a handwritten character
     * @param userStrokes List of stroke paths drawn by user
     * @param targetCharacter Expected character (for validation)
     * @return RecognitionResult with confidence and feedback
     */
    fun recognize(
        userStrokes: List<List<PointF>>,
        targetCharacter: String
    ): RecognitionResult {
        // Preprocess strokes to normalized input
        val inputBuffer = preprocessStrokes(userStrokes)
        
        return if (interpreter != null && inputBuffer != null) {
            // ML-based recognition
            mlRecognize(inputBuffer, targetCharacter)
        } else {
            // Fallback to heuristic recognition
            heuristicRecognize(userStrokes, targetCharacter)
        }
    }
    
    private fun mlRecognize(
        inputBuffer: Array<Array<Array<FloatArray>>>,
        targetCharacter: String
    ): RecognitionResult {
        val outputBuffer = Array(1) { FloatArray(numClasses) }
        
        interpreter?.run(inputBuffer, outputBuffer)
        
        val predictions = outputBuffer[0]
        val targetIndex = characterIndexMap[targetCharacter] ?: 0
        val confidence = predictions[targetIndex]
        
        // Find top 3 predictions for feedback
        val topPredictions = predictions
            .withIndex()
            .sortedByDescending { it.value }
            .take(3)
            .map { (index, conf) ->
                CharacterPrediction(
                    character = indexCharacterMap[index] ?: "?",
                    confidence = conf
                )
            }
        
        return RecognitionResult(
            isCorrect = confidence > CORRECT_THRESHOLD,
            confidence = confidence,
            shapeAccuracy = confidence,
            strokeOrderCorrect = true, // ML doesn't check stroke order
            topPredictions = topPredictions,
            feedback = generateFeedback(confidence, targetCharacter, topPredictions)
        )
    }
    
    private fun heuristicRecognize(
        userStrokes: List<List<PointF>>,
        targetCharacter: String
    ): RecognitionResult {
        // Fallback heuristic-based recognition
        // Compare stroke count, bounding box ratios, and basic shape
        
        val targetStrokes = loadTargetStrokes(targetCharacter)
        
        // Check stroke count
        val strokeCountMatch = userStrokes.size == targetStrokes.size
        
        // Normalize and compare each stroke
        val strokeScores = userStrokes.mapIndexed { index, userStroke ->
            if (index < targetStrokes.size) {
                compareStrokes(userStroke, targetStrokes[index])
            } else 0f
        }
        
        val avgScore = strokeScores.average().toFloat()
        val confidence = if (strokeCountMatch) avgScore else avgScore * 0.7f
        
        return RecognitionResult(
            isCorrect = confidence > CORRECT_THRESHOLD,
            confidence = confidence,
            shapeAccuracy = avgScore,
            strokeOrderCorrect = strokeCountMatch,
            topPredictions = emptyList(),
            feedback = generateHeuristicFeedback(confidence, strokeCountMatch, userStrokes.size, targetStrokes.size)
        )
    }
    
    /**
     * Preprocess user strokes into model input format
     */
    private fun preprocessStrokes(
        strokes: List<List<PointF>>
    ): Array<Array<Array<FloatArray>>>? {
        // Create 64x64 normalized image from strokes
        val grid = Array(inputSize) { Array(inputSize) { FloatArray(1) { 0f } } }
        
        // Find bounding box
        val allPoints = strokes.flatten()
        if (allPoints.isEmpty()) return null
        
        val minX = allPoints.minOf { it.x }
        val maxX = allPoints.maxOf { it.x }
        val minY = allPoints.minOf { it.y }
        val maxY = allPoints.maxOf { it.y }
        
        val width = maxX - minX
        val height = maxY - minY
        val scale = maxOf(width, height)
        
        if (scale == 0f) return null
        
        // Normalize and rasterize strokes
        strokes.forEach { stroke ->
            for (i in 0 until stroke.size - 1) {
                val p1 = stroke[i]
                val p2 = stroke[i + 1]
                
                val x1 = ((p1.x - minX) / scale * (inputSize - 1)).toInt()
                val y1 = ((p1.y - minY) / scale * (inputSize - 1)).toInt()
                val x2 = ((p2.x - minX) / scale * (inputSize - 1)).toInt()
                val y2 = ((p2.y - minY) / scale * (inputSize - 1)).toInt()
                
                // Draw line between points
                drawLineOnGrid(grid, x1, y1, x2, y2)
            }
        }
        
        return arrayOf(grid)
    }
    
    private fun drawLineOnGrid(
        grid: Array<Array<FloatArray>>,
        x1: Int, y1: Int,
        x2: Int, y2: Int
    ) {
        // Bresenham's line algorithm
        var x = x1
        var y = y1
        val dx = kotlin.math.abs(x2 - x1)
        val dy = kotlin.math.abs(y2 - y1)
        val sx = if (x1 < x2) 1 else -1
        val sy = if (y1 < y2) 1 else -1
        var err = dx - dy
        
        while (true) {
            if (x in 0 until inputSize && y in 0 until inputSize) {
                grid[y][x][0] = 1f
            }
            
            if (x == x2 && y == y2) break
            
            val e2 = 2 * err
            if (e2 > -dy) {
                err -= dy
                x += sx
            }
            if (e2 < dx) {
                err += dx
                y += sy
            }
        }
    }
    
    private fun compareStrokes(user: List<PointF>, target: StrokeData): Float {
        // Normalize user stroke
        val normalizedUser = normalizePoints(user)
        
        // Compare using Dynamic Time Warping
        return calculateDTW(normalizedUser, target.path)
    }
    
    private fun compareStrokes(user: List<PointF>, target: List<PointF>): Float {
        // Normalize user stroke
        val normalizedUser = normalizePoints(user)
        
        // Compare using Dynamic Time Warping
        return calculateDTW(normalizedUser, target)
    }
    
    private fun normalizePoints(points: List<PointF>): List<PointF> {
        if (points.isEmpty()) return emptyList()
        
        val minX = points.minOf { it.x }
        val maxX = points.maxOf { it.x }
        val minY = points.minOf { it.y }
        val maxY = points.maxOf { it.y }
        
        val scaleX = maxX - minX
        val scaleY = maxY - minY
        val scale = maxOf(scaleX, scaleY)
        
        return if (scale > 0) {
            points.map { p ->
                PointF(
                    (p.x - minX) / scale,
                    (p.y - minY) / scale
                )
            }
        } else points
    }
    
    private fun calculateDTW(user: List<PointF>, target: List<PointF>): Float {
        val n = user.size
        val m = target.size
        if (n == 0 || m == 0) return 0f
        
        val dp = Array(n + 1) { FloatArray(m + 1) { Float.MAX_VALUE } }
        dp[0][0] = 0f
        
        for (i in 1..n) {
            for (j in 1..m) {
                val cost = euclideanDistance(user[i - 1], target[j - 1])
                dp[i][j] = cost + minOf(
                    dp[i - 1][j],
                    dp[i][j - 1],
                    dp[i - 1][j - 1]
                )
            }
        }
        
        val maxDist = kotlin.math.sqrt(2f) * maxOf(n, m)
        return 1f - (dp[n][m] / maxDist).coerceIn(0f, 1f)
    }
    
    private fun euclideanDistance(p1: PointF, p2: PointF): Float {
        val dx = p1.x - p2.x
        val dy = p1.y - p2.y
        return kotlin.math.sqrt(dx * dx + dy * dy)
    }
    
    private fun loadTargetStrokes(character: String): List<StrokeData> {
        // Load from KanaStrokeData or KanjiStrokeData
        return KanaStrokeData.getStrokes(character)
    }
    
    private fun loadTargetStrokeList(character: String): List<List<PointF>> {
        return KanaStrokeData.getStrokes(character).map { it.path }
    }
    
    private fun generateFeedback(
        confidence: Float,
        target: String,
        predictions: List<CharacterPrediction>
    ): String {
        return when {
            confidence > 0.95f -> "Perfect! 🌟"
            confidence > 0.85f -> "Great job! 🎉"
            confidence > 0.70f -> "Good, but keep practicing! 💪"
            else -> "Try again! Watch the stroke order. 💭"
        }
    }
    
    private fun generateHeuristicFeedback(
        confidence: Float,
        strokeCountMatch: Boolean,
        userCount: Int,
        targetCount: Int
    ): String {
        return buildString {
            if (!strokeCountMatch) {
                append("Stroke count: $userCount / $targetCount. ")
            }
            when {
                confidence > 0.85f -> append("Excellent shape! 🌟")
                confidence > 0.60f -> append("Getting there! Watch your proportions. 📐")
                else -> append("Try tracing the guide more closely. ✍️")
            }
        }
    }
    
    companion object {
        private const val CORRECT_THRESHOLD = 0.75f
    }
    
    fun close() {
        interpreter?.close()
    }
}

/**
 * Result of character recognition
 */
data class RecognitionResult(
    val isCorrect: Boolean,
    val confidence: Float,
    val shapeAccuracy: Float,
    val strokeOrderCorrect: Boolean,
    val topPredictions: List<CharacterPrediction>,
    val feedback: String
)

/**
 * Character prediction with confidence
 */
data class CharacterPrediction(
    val character: String,
    val confidence: Float
)

/**
 * Stroke order validator
 */
class StrokeOrderValidator {

    /**
     * Validate if user drew strokes in correct order
     */
    fun validateStrokeOrder(
        userStrokes: List<List<PointF>>,
        targetStrokes: List<StrokeData>
    ): StrokeOrderValidation {
        if (userStrokes.size != targetStrokes.size) {
            return StrokeOrderValidation(
                isCorrect = false,
                correctCount = 0,
                errors = listOf("Incorrect number of strokes")
            )
        }

        var correctCount = 0
        val errors = mutableListOf<String>()

        userStrokes.forEachIndexed { index, userStroke ->
            val target = targetStrokes[index]

            // Check if stroke starts in correct area
            val normalizedUser = normalizeForValidation(userStroke)
            val userStart = normalizedUser.firstOrNull()

            if (userStart != null) {
                val startDiff = euclideanDistance(userStart, target.startPoint)
                if (startDiff < 0.3f) {
                    correctCount++
                } else {
                    errors.add("Stroke ${index + 1} started in wrong position")
                }
            }
        }

        return StrokeOrderValidation(
            isCorrect = correctCount == targetStrokes.size,
            correctCount = correctCount,
            errors = errors
        )
    }

    private fun normalizeForValidation(points: List<PointF>): List<PointF> {
        if (points.isEmpty()) return emptyList()

        val minX = points.minOf { it.x }
        val maxX = points.maxOf { it.x }
        val minY = points.minOf { it.y }
        val maxY = points.maxOf { it.y }

        val width = maxX - minX
        val height = maxY - minY

        return if (width > 0 && height > 0) {
            points.map { p ->
                PointF(
                    (p.x - minX) / width,
                    (p.y - minY) / height
                )
            }
        } else points
    }

    private fun euclideanDistance(p1: PointF, p2: PointF): Float {
        val dx = p1.x - p2.x
        val dy = p1.y - p2.y
        return kotlin.math.sqrt(dx * dx + dy * dy)
    }
}

data class StrokeOrderValidation(
    val isCorrect: Boolean,
    val correctCount: Int,
    val errors: List<String>
)