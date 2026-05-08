package com.cutekana.ui.components

import android.view.MotionEvent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.cutekana.data.model.PointF
import com.cutekana.data.model.StrokeData
import com.cutekana.data.model.StrokeDirection
import com.cutekana.data.model.KanaStrokeData
import com.cutekana.ui.theme.Mint
import com.cutekana.ui.theme.SakuraPink
import com.cutekana.ui.theme.StarYellow

/**
 * Main writing canvas component for stroke practice
 * Supports guided tracing and free writing modes
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WritingCanvas(
    character: String,
    strokes: List<StrokeData>,
    currentStrokeIndex: Int,
    mode: WritingMode,
    onStrokeComplete: (List<PointF>) -> Unit,
    recognitionResult: com.cutekana.data.ml.RecognitionResult?,
    modifier: Modifier = Modifier,
    showGuide: Boolean = true
) {
    val density = LocalDensity.current
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }

    var currentPath by remember { mutableStateOf(Path()) }
    var currentPoints by remember { mutableStateOf<List<PointF>>(emptyList()) }
    var completedPaths by remember { mutableStateOf<List<Pair<Path, Boolean>>>(emptyList()) }
    
    // Animation for guide stroke
    val guideAnimation = rememberInfiniteTransition(label = "guide")
    val guideProgress by guideAnimation.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "guide_animation"
    )
    
    // Particle effect for correct strokes
    var particles by remember { mutableStateOf(listOf<Particle>()) }
    
    LaunchedEffect(recognitionResult) {
        if (recognitionResult?.isCorrect == true) {
            // Add celebration particles
            particles = particles + List(20) {
                Particle(
                    x = canvasSize.width / 2f,
                    y = canvasSize.height / 2f,
                    velocityX = (Math.random() * 200 - 100).toFloat(),
                    velocityY = (Math.random() * 200 - 100).toFloat(),
                    color = if (Math.random() > 0.5) SakuraPink else StarYellow,
                    size = (5..15).random().toFloat()
                )
            }
        }
    }
    
    val outlineColor = MaterialTheme.colorScheme.outline
    val primaryColor = MaterialTheme.colorScheme.primary
    val surfaceColor = MaterialTheme.colorScheme.surface

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(24.dp))
            .background(surfaceColor)
            .onSizeChanged { canvasSize = it }
    ) {
        // Grid background (田字格)
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCharacterGrid()
        }

        // Reference outline
        if (showGuide) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                strokes.forEachIndexed { index, stroke ->
                    val alpha = when {
                        index < currentStrokeIndex -> 0.1f
                        index == currentStrokeIndex -> 0.3f
                        else -> 0.05f
                    }

                    drawStrokePath(
                        stroke = stroke,
                        color = outlineColor.copy(alpha = alpha),
                        strokeWidth = 4f
                    )
                }
            }
        }
        
        // Animated guide for current stroke
        if (mode == WritingMode.GUIDED && currentStrokeIndex < strokes.size) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val currentStroke = strokes[currentStrokeIndex]
                val path = createPathFromStroke(currentStroke)
                
                // Draw progressing guide
                val pathMeasure = PathMeasure()
                pathMeasure.setPath(path, false)
                val length = pathMeasure.length
                
                val partialPath = Path()
                pathMeasure.getSegment(0f, length * guideProgress, partialPath, true)
                
                drawPath(
                    path = partialPath,
                    color = SakuraPink.copy(alpha = 0.8f),
                    style = Stroke(
                        width = 6f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
                
                // Draw start point indicator
                if (guideProgress < 0.1f) {
                    drawCircle(
                        color = Mint,
                        radius = 12f,
                        center = Offset(
                            currentStroke.startPoint.x * size.width,
                            currentStroke.startPoint.y * size.height
                        )
                    )
                }
            }
        }
        
        // User drawing canvas
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInteropFilter { event ->
                    val x = event.x / canvasSize.width.toFloat()
                    val y = event.y / canvasSize.height.toFloat()

                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            currentPath = Path().apply {
                                moveTo(event.x, event.y)
                            }
                            currentPoints = listOf(PointF(x, y))
                            true
                        }
                        MotionEvent.ACTION_MOVE -> {
                            currentPath.lineTo(event.x, event.y)
                            currentPoints = currentPoints + PointF(x, y)
                            true
                        }
                        MotionEvent.ACTION_UP -> {
                            completedPaths = completedPaths + (currentPath to true)
                            onStrokeComplete(currentPoints)
                            currentPath = Path()
                            currentPoints = emptyList()
                            true
                        }
                        else -> false
                    }
                }
        ) {
            // Draw completed strokes
            completedPaths.forEach { (path, _) ->
                drawPath(
                    path = path,
                    color = primaryColor,
                    style = Stroke(
                        width = 8f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }

            // Draw current stroke
            drawPath(
                path = currentPath,
                color = primaryColor,
                style = Stroke(
                    width = 8f,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
        
        // Particle effects
        ParticleEffect(particles = particles)
    }
}

/**
 * Character grid (田字格) for writing guidance
 */
private fun DrawScope.drawCharacterGrid() {
    val strokeWidth = 1f
    val color = Color.Gray.copy(alpha = 0.2f)
    
    // Outer border
    drawRect(
        color = color,
        topLeft = Offset.Zero,
        size = size,
        style = Stroke(width = strokeWidth * 2)
    )
    
    // Dashed center vertical
    drawDashedLine(
        start = Offset(size.width / 2, 0f),
        end = Offset(size.width / 2, size.height),
        color = color,
        strokeWidth = strokeWidth
    )
    
    // Dashed center horizontal
    drawDashedLine(
        start = Offset(0f, size.height / 2),
        end = Offset(size.width, size.height / 2),
        color = color,
        strokeWidth = strokeWidth
    )
    
    // Diagonal lines (optional)
    drawLine(
        color = color.copy(alpha = 0.1f),
        start = Offset(0f, 0f),
        end = Offset(size.width, size.height),
        strokeWidth = strokeWidth
    )
    drawLine(
        color = color.copy(alpha = 0.1f),
        start = Offset(size.width, 0f),
        end = Offset(0f, size.height),
        strokeWidth = strokeWidth
    )
}

private fun DrawScope.drawDashedLine(
    start: Offset,
    end: Offset,
    color: Color,
    strokeWidth: Float
) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    drawLine(
        color = color,
        start = start,
        end = end,
        strokeWidth = strokeWidth,
        pathEffect = pathEffect
    )
}

private fun DrawScope.drawStrokePath(
    stroke: StrokeData,
    color: Color,
    strokeWidth: Float
) {
    val path = createPathFromStroke(stroke)
    drawPath(
        path = path,
        color = color,
        style = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}

private fun createPathFromStroke(stroke: StrokeData): Path {
    return Path().apply {
        if (stroke.path.isNotEmpty()) {
            val first = stroke.path.first()
            moveTo(first.x, first.y)
            
            for (i in 1 until stroke.path.size) {
                val point = stroke.path[i]
                lineTo(point.x, point.y)
            }
        }
    }
}

/**
 * Particle effect for celebrations
 */
@Composable
fun ParticleEffect(
    particles: List<Particle>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        particles.forEach { particle ->
            val animatedX by animateFloatAsState(
                targetValue = particle.x + particle.velocityX,
                animationSpec = tween(1000, easing = FastOutSlowInEasing),
                label = "particle_x"
            )
            val animatedY by animateFloatAsState(
                targetValue = particle.y + particle.velocityY,
                animationSpec = tween(1000, easing = FastOutSlowInEasing),
                label = "particle_y"
            )
            val alpha by animateFloatAsState(
                targetValue = 0f,
                animationSpec = tween(1000),
                label = "particle_alpha"
            )
            
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = particle.color.copy(alpha = alpha),
                    radius = particle.size,
                    center = Offset(animatedX, animatedY)
                )
            }
        }
    }
}

/**
 * Stroke order indicator showing progress
 */
@Composable
fun StrokeOrderIndicator(
    totalStrokes: Int,
    currentStroke: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(totalStrokes) { index ->
            val isCompleted = index < currentStroke
            val isCurrent = index == currentStroke
            
            val color = when {
                isCompleted -> Mint
                isCurrent -> SakuraPink
                else -> MaterialTheme.colorScheme.outline
            }
            
            val size by animateDpAsState(
                targetValue = if (isCurrent) 16.dp else 12.dp,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                label = "indicator_size"
            )
            
            Box(
                modifier = Modifier
                    .size(size)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted) {
                    Text(
                        text = "✓",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                } else if (isCurrent) {
                    Text(
                        text = "${index + 1}",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

/**
 * Writing feedback display
 */
@Composable
fun WritingFeedback(
    result: com.cutekana.data.ml.RecognitionResult?,
    modifier: Modifier = Modifier
) {
    if (result == null) return
    
    val backgroundColor = if (result.isCorrect) 
        Mint.copy(alpha = 0.2f) 
    else 
        MaterialTheme.colorScheme.errorContainer
    
    val textColor = if (result.isCorrect) 
        Mint 
    else 
        MaterialTheme.colorScheme.error
    
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = result.feedback,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            
            if (!result.isCorrect && result.confidence > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                CuteProgressBar(
                    progress = result.confidence,
                    color = textColor,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Accuracy: ${(result.confidence * 100).toInt()}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor
                )
            }
        }
    }
}

data class Particle(
    val x: Float,
    val y: Float,
    val velocityX: Float,
    val velocityY: Float,
    val color: Color,
    val size: Float
)

enum class WritingMode {
    GUIDED,     // Show animated stroke guide
    REFERENCE,  // Show faded outline
    MEMORY,     // No guide, write from memory
    SPEED       // Timed writing challenge
}

/**
 * Manual writing canvas - collects strokes but doesn't auto-submit
 * User must tap Check button to validate
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WritingCanvasManual(
    character: String,
    strokes: List<StrokeData>,
    currentStrokeIndex: Int,
    mode: WritingMode,
    onStrokeDrawn: (List<PointF>) -> Unit,
    recognitionResult: com.cutekana.data.ml.RecognitionResult?,
    modifier: Modifier = Modifier,
    showGuide: Boolean = true
) {
    val density = LocalDensity.current
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }

    var currentPath by remember { mutableStateOf(Path()) }
    var currentPoints by remember { mutableStateOf<List<PointF>>(emptyList()) }
    var completedPaths by remember { mutableStateOf<List<Pair<Path, Boolean>>>(emptyList()) }
    
    // Animation for guide stroke
    val guideAnimation = rememberInfiniteTransition(label = "guide")
    val guideProgress by guideAnimation.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "guide_animation"
    )
    
    // Particle effect for correct strokes
    var particles by remember { mutableStateOf(listOf<Particle>()) }
    
    // Clear state when moving to next character/stroke
    LaunchedEffect(character, currentStrokeIndex) {
        currentPath = Path()
        currentPoints = emptyList()
        completedPaths = emptyList()
        particles = emptyList()
    }
    
    LaunchedEffect(recognitionResult) {
        if (recognitionResult?.isCorrect == true) {
            // Add celebration particles
            particles = particles + List(20) {
                Particle(
                    x = canvasSize.width / 2f,
                    y = canvasSize.height / 2f,
                    velocityX = (Math.random() * 200 - 100).toFloat(),
                    velocityY = (Math.random() * 200 - 100).toFloat(),
                    color = if (Math.random() > 0.5) SakuraPink else StarYellow,
                    size = (5..15).random().toFloat()
                )
            }
        }
    }
    
    val outlineColor = MaterialTheme.colorScheme.outline
    val primaryColor = MaterialTheme.colorScheme.primary
    val errorColor = MaterialTheme.colorScheme.error
    val surfaceColor = MaterialTheme.colorScheme.surface

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(24.dp))
            .background(surfaceColor)
            .onSizeChanged { canvasSize = it }
    ) {
        // Grid background (田字格)
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCharacterGrid()
        }

        // Reference outline
        if (showGuide) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                strokes.forEachIndexed { index, stroke ->
                    val alpha = when {
                        index < currentStrokeIndex -> 0.1f
                        index == currentStrokeIndex -> 0.3f
                        else -> 0.05f
                    }

                    drawStrokePath(
                        stroke = stroke,
                        color = outlineColor.copy(alpha = alpha),
                        strokeWidth = 4f
                    )
                }
            }
        }
        
        // Animated guide for current stroke
        if (mode == WritingMode.GUIDED && currentStrokeIndex < strokes.size) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val currentStroke = strokes[currentStrokeIndex]
                val path = createPathFromStroke(currentStroke)
                
                // Draw progressing guide
                val pathMeasure = PathMeasure()
                pathMeasure.setPath(path, false)
                val length = pathMeasure.length
                
                val partialPath = Path()
                pathMeasure.getSegment(0f, length * guideProgress, partialPath, true)
                
                drawPath(
                    path = partialPath,
                    color = SakuraPink.copy(alpha = 0.8f),
                    style = Stroke(
                        width = 6f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
                
                // Draw start point indicator
                if (guideProgress < 0.1f) {
                    drawCircle(
                        color = Mint,
                        radius = 12f,
                        center = Offset(
                            currentStroke.startPoint.x * size.width,
                            currentStroke.startPoint.y * size.height
                        )
                    )
                }
            }
        }
        
        // User drawing canvas
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInteropFilter { event ->
                    val x = event.x / canvasSize.width.toFloat()
                    val y = event.y / canvasSize.height.toFloat()

                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            currentPath = Path().apply {
                                moveTo(event.x, event.y)
                            }
                            currentPoints = listOf(PointF(x, y))
                            true
                        }
                        MotionEvent.ACTION_MOVE -> {
                            currentPath.lineTo(event.x, event.y)
                            currentPoints = currentPoints + PointF(x, y)
                            true
                        }
                        MotionEvent.ACTION_UP -> {
                            // Don't auto-submit! Just notify parent that stroke is drawn
                            completedPaths = completedPaths + (currentPath to true)
                            onStrokeDrawn(currentPoints)
                            true
                        }
                        else -> false
                    }
                }
        ) {
            // Draw completed strokes - show red if incorrect
            val strokeColor = if (recognitionResult?.isCorrect == false) errorColor else primaryColor
            
            completedPaths.forEach { (path, _) ->
                drawPath(
                    path = path,
                    color = strokeColor,
                    style = Stroke(
                        width = 8f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }

            // Draw current stroke
            drawPath(
                path = currentPath,
                color = strokeColor,
                style = Stroke(
                    width = 8f,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
        
        // Particle effects
        ParticleEffect(particles = particles)
    }
}
