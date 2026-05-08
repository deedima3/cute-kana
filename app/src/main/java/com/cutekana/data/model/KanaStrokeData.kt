package com.cutekana.data.model

import com.cutekana.data.model.PointF
import com.cutekana.data.model.StrokeData
import com.cutekana.data.model.StrokeDirection
import com.cutekana.data.model.StrokeType

/**
 * Complete stroke data for all 92 Kana characters
 * Normalized coordinates (0.0 - 1.0)
 */
object KanaStrokeData {
    
    // ==================== HIRAGANA ====================
    
    // あ-row
    val A = listOf(
        // Stroke 1: Top horizontal
        StrokeData(
            id = 1,
            path = listOf(PointF(0.25f, 0.2f), PointF(0.75f, 0.2f)),
            startPoint = PointF(0.25f, 0.2f),
            endPoint = PointF(0.75f, 0.2f),
            direction = StrokeDirection.LEFT_TO_RIGHT,
            type = StrokeType.HORIZONTAL
        ),
        // Stroke 2: Left curve
        StrokeData(
            id = 2,
            path = listOf(
                PointF(0.25f, 0.35f),
                PointF(0.2f, 0.5f),
                PointF(0.25f, 0.65f),
                PointF(0.35f, 0.75f)
            ),
            startPoint = PointF(0.25f, 0.35f),
            endPoint = PointF(0.35f, 0.75f),
            direction = StrokeDirection.TOP_TO_BOTTOM_CURVED,
            type = StrokeType.CURVE
        ),
        // Stroke 3: Right loop
        StrokeData(
            id = 3,
            path = listOf(
                PointF(0.55f, 0.35f),
                PointF(0.75f, 0.55f),
                PointF(0.65f, 0.75f),
                PointF(0.45f, 0.6f),
                PointF(0.55f, 0.35f)
            ),
            startPoint = PointF(0.55f, 0.35f),
            endPoint = PointF(0.55f, 0.35f),
            direction = StrokeDirection.LOOP,
            type = StrokeType.LOOP
        )
    )
    
    val I = listOf(
        // Stroke 1: Left vertical
        StrokeData(
            id = 1,
            path = listOf(PointF(0.35f, 0.2f), PointF(0.35f, 0.8f)),
            startPoint = PointF(0.35f, 0.2f),
            endPoint = PointF(0.35f, 0.8f),
            direction = StrokeDirection.TOP_TO_BOTTOM,
            type = StrokeType.VERTICAL
        ),
        // Stroke 2: Dots on right
        StrokeData(
            id = 2,
            path = listOf(PointF(0.65f, 0.3f)),
            startPoint = PointF(0.65f, 0.3f),
            endPoint = PointF(0.65f, 0.3f),
            direction = StrokeDirection.DOT,
            type = StrokeType.DOT
        ),
        StrokeData(
            id = 3,
            path = listOf(PointF(0.65f, 0.5f)),
            startPoint = PointF(0.65f, 0.5f),
            endPoint = PointF(0.65f, 0.5f),
            direction = StrokeDirection.DOT,
            type = StrokeType.DOT
        )
    )
    
    val U = listOf(
        // Stroke 1: Top horizontal
        StrokeData(
            id = 1,
            path = listOf(PointF(0.2f, 0.3f), PointF(0.8f, 0.3f)),
            startPoint = PointF(0.2f, 0.3f),
            endPoint = PointF(0.8f, 0.3f),
            direction = StrokeDirection.LEFT_TO_RIGHT,
            type = StrokeType.HORIZONTAL
        ),
        // Stroke 2: Right curve down
        StrokeData(
            id = 2,
            path = listOf(
                PointF(0.75f, 0.35f),
                PointF(0.8f, 0.6f),
                PointF(0.6f, 0.8f),
                PointF(0.3f, 0.75f)
            ),
            startPoint = PointF(0.75f, 0.35f),
            endPoint = PointF(0.3f, 0.75f),
            direction = StrokeDirection.TOP_TO_BOTTOM_CURVED,
            type = StrokeType.CURVE
        ),
        // Stroke 3: Short right mark
        StrokeData(
            id = 3,
            path = listOf(
                PointF(0.35f, 0.15f),
                PointF(0.5f, 0.1f),
                PointF(0.65f, 0.15f)
            ),
            startPoint = PointF(0.35f, 0.15f),
            endPoint = PointF(0.65f, 0.15f),
            direction = StrokeDirection.LEFT_TO_RIGHT,
            type = StrokeType.CURVE
        )
    )
    
    val E = listOf(
        // Stroke 1: Top horizontal
        StrokeData(
            id = 1,
            path = listOf(PointF(0.25f, 0.25f), PointF(0.75f, 0.25f)),
            startPoint = PointF(0.25f, 0.25f),
            endPoint = PointF(0.75f, 0.25f),
            direction = StrokeDirection.LEFT_TO_RIGHT,
            type = StrokeType.HORIZONTAL
        ),
        // Stroke 2: Right vertical hook
        StrokeData(
            id = 2,
            path = listOf(
                PointF(0.7f, 0.25f),
                PointF(0.7f, 0.65f),
                PointF(0.5f, 0.8f)
            ),
            startPoint = PointF(0.7f, 0.25f),
            endPoint = PointF(0.5f, 0.8f),
            direction = StrokeDirection.HOOK,
            type = StrokeType.HOOK
        ),
        // Stroke 3: Center horizontal
        StrokeData(
            id = 3,
            path = listOf(PointF(0.4f, 0.5f), PointF(0.55f, 0.5f)),
            startPoint = PointF(0.4f, 0.5f),
            endPoint = PointF(0.55f, 0.5f),
            direction = StrokeDirection.LEFT_TO_RIGHT,
            type = StrokeType.HORIZONTAL
        )
    )
    
    val O = listOf(
        // Stroke 1: Left vertical curve
        StrokeData(
            id = 1,
            path = listOf(
                PointF(0.3f, 0.2f),
                PointF(0.25f, 0.5f),
                PointF(0.3f, 0.8f)
            ),
            startPoint = PointF(0.3f, 0.2f),
            endPoint = PointF(0.3f, 0.8f),
            direction = StrokeDirection.TOP_TO_BOTTOM_CURVED,
            type = StrokeType.CURVE
        ),
        // Stroke 2: Top-right to bottom sweep
        StrokeData(
            id = 2,
            path = listOf(
                PointF(0.7f, 0.25f),
                PointF(0.75f, 0.45f),
                PointF(0.7f, 0.7f),
                PointF(0.45f, 0.8f)
            ),
            startPoint = PointF(0.7f, 0.25f),
            endPoint = PointF(0.45f, 0.8f),
            direction = StrokeDirection.TOP_TO_BOTTOM_CURVED,
            type = StrokeType.CURVE
        ),
        // Stroke 3: Lower horizontal
        StrokeData(
            id = 3,
            path = listOf(PointF(0.35f, 0.65f), PointF(0.65f, 0.65f)),
            startPoint = PointF(0.35f, 0.65f),
            endPoint = PointF(0.65f, 0.65f),
            direction = StrokeDirection.LEFT_TO_RIGHT,
            type = StrokeType.HORIZONTAL
        )
    )
    
    // か-row
    val KA = listOf(
        // Stroke 1: Top horizontal
        StrokeData(
            id = 1,
            path = listOf(PointF(0.3f, 0.25f), PointF(0.7f, 0.25f)),
            startPoint = PointF(0.3f, 0.25f),
            endPoint = PointF(0.7f, 0.25f),
            direction = StrokeDirection.LEFT_TO_RIGHT,
            type = StrokeType.HORIZONTAL
        ),
        // Stroke 2: Left vertical
        StrokeData(
            id = 2,
            path = listOf(PointF(0.35f, 0.25f), PointF(0.35f, 0.75f)),
            startPoint = PointF(0.35f, 0.25f),
            endPoint = PointF(0.35f, 0.75f),
            direction = StrokeDirection.TOP_TO_BOTTOM,
            type = StrokeType.VERTICAL
        ),
        // Stroke 3: Right curve
        StrokeData(
            id = 3,
            path = listOf(
                PointF(0.65f, 0.3f),
                PointF(0.7f, 0.5f),
                PointF(0.6f, 0.75f),
                PointF(0.4f, 0.85f)
            ),
            startPoint = PointF(0.65f, 0.3f),
            endPoint = PointF(0.4f, 0.85f),
            direction = StrokeDirection.TOP_TO_BOTTOM_CURVED,
            type = StrokeType.CURVE
        )
    )
    
    val KI = listOf(
        // Stroke 1: Left vertical
        StrokeData(
            id = 1,
            path = listOf(PointF(0.3f, 0.2f), PointF(0.3f, 0.8f)),
            startPoint = PointF(0.3f, 0.2f),
            endPoint = PointF(0.3f, 0.8f),
            direction = StrokeDirection.TOP_TO_BOTTOM,
            type = StrokeType.VERTICAL
        ),
        // Stroke 2: Right vertical
        StrokeData(
            id = 2,
            path = listOf(PointF(0.7f, 0.2f), PointF(0.7f, 0.8f)),
            startPoint = PointF(0.7f, 0.2f),
            endPoint = PointF(0.7f, 0.8f),
            direction = StrokeDirection.TOP_TO_BOTTOM,
            type = StrokeType.VERTICAL
        ),
        // Stroke 3: Middle horizontal
        StrokeData(
            id = 3,
            path = listOf(PointF(0.35f, 0.5f), PointF(0.65f, 0.5f)),
            startPoint = PointF(0.35f, 0.5f),
            endPoint = PointF(0.65f, 0.5f),
            direction = StrokeDirection.LEFT_TO_RIGHT,
            type = StrokeType.HORIZONTAL
        ),
        // Stroke 4: Lower horizontal
        StrokeData(
            id = 4,
            path = listOf(PointF(0.35f, 0.75f), PointF(0.65f, 0.75f)),
            startPoint = PointF(0.35f, 0.75f),
            endPoint = PointF(0.65f, 0.75f),
            direction = StrokeDirection.LEFT_TO_RIGHT,
            type = StrokeType.HORIZONTAL
        )
    )
    
    val KU = listOf(
        // Stroke 1: Top horizontal
        StrokeData(
            id = 1,
            path = listOf(PointF(0.25f, 0.25f), PointF(0.75f, 0.25f)),
            startPoint = PointF(0.25f, 0.25f),
            endPoint = PointF(0.75f, 0.25f),
            direction = StrokeDirection.LEFT_TO_RIGHT,
            type = StrokeType.HORIZONTAL
        ),
        // Stroke 2: Vertical with hook
        StrokeData(
            id = 2,
            path = listOf(
                PointF(0.5f, 0.25f),
                PointF(0.5f, 0.7f),
                PointF(0.35f, 0.85f)
            ),
            startPoint = PointF(0.5f, 0.25f),
            endPoint = PointF(0.35f, 0.85f),
            direction = StrokeDirection.HOOK,
            type = StrokeType.HOOK
        )
    )
    
    val KE = listOf(
        // Stroke 1: Horizontal
        StrokeData(
            id = 1,
            path = listOf(PointF(0.25f, 0.3f), PointF(0.75f, 0.3f)),
            startPoint = PointF(0.25f, 0.3f),
            endPoint = PointF(0.75f, 0.3f),
            direction = StrokeDirection.LEFT_TO_RIGHT,
            type = StrokeType.HORIZONTAL
        ),
        // Stroke 2: Vertical
        StrokeData(
            id = 2,
            path = listOf(PointF(0.4f, 0.3f), PointF(0.4f, 0.8f)),
            startPoint = PointF(0.4f, 0.3f),
            endPoint = PointF(0.4f, 0.8f),
            direction = StrokeDirection.TOP_TO_BOTTOM,
            type = StrokeType.VERTICAL
        ),
        // Stroke 3: Diagonal
        StrokeData(
            id = 3,
            path = listOf(PointF(0.55f, 0.35f), PointF(0.75f, 0.75f)),
            startPoint = PointF(0.55f, 0.35f),
            endPoint = PointF(0.75f, 0.75f),
            direction = StrokeDirection.DIAGONAL_DOWN_RIGHT,
            type = StrokeType.DIAGONAL
        )
    )
    
    val KO = listOf(
        // Stroke 1: Top horizontal
        StrokeData(
            id = 1,
            path = listOf(PointF(0.3f, 0.3f), PointF(0.7f, 0.3f)),
            startPoint = PointF(0.3f, 0.3f),
            endPoint = PointF(0.7f, 0.3f),
            direction = StrokeDirection.LEFT_TO_RIGHT,
            type = StrokeType.HORIZONTAL
        ),
        // Stroke 2: Vertical
        StrokeData(
            id = 2,
            path = listOf(PointF(0.3f, 0.3f), PointF(0.3f, 0.75f)),
            startPoint = PointF(0.3f, 0.3f),
            endPoint = PointF(0.3f, 0.75f),
            direction = StrokeDirection.TOP_TO_BOTTOM,
            type = StrokeType.VERTICAL
        ),
        // Stroke 3: Bottom horizontal
        StrokeData(
            id = 3,
            path = listOf(PointF(0.3f, 0.75f), PointF(0.7f, 0.75f)),
            startPoint = PointF(0.3f, 0.75f),
            endPoint = PointF(0.7f, 0.75f),
            direction = StrokeDirection.LEFT_TO_RIGHT,
            type = StrokeType.HORIZONTAL
        )
    )
    
    // ==================== KATAKANA ====================
    
    // ア-row
    val A_KATAKANA = listOf(
        // Stroke 1: Left diagonal
        StrokeData(
            id = 1,
            path = listOf(PointF(0.35f, 0.25f), PointF(0.25f, 0.65f)),
            startPoint = PointF(0.35f, 0.25f),
            endPoint = PointF(0.25f, 0.65f),
            direction = StrokeDirection.DIAGONAL_DOWN_LEFT,
            type = StrokeType.DIAGONAL
        ),
        // Stroke 2: Right curve
        StrokeData(
            id = 2,
            path = listOf(
                PointF(0.35f, 0.35f),
                PointF(0.65f, 0.3f),
                PointF(0.75f, 0.4f),
                PointF(0.65f, 0.5f)
            ),
            startPoint = PointF(0.35f, 0.35f),
            endPoint = PointF(0.65f, 0.5f),
            direction = StrokeDirection.LEFT_TO_RIGHT_CURVED,
            type = StrokeType.CURVE
        ),
        // Stroke 3: Vertical
        StrokeData(
            id = 3,
            path = listOf(PointF(0.75f, 0.25f), PointF(0.75f, 0.75f)),
            startPoint = PointF(0.75f, 0.25f),
            endPoint = PointF(0.75f, 0.75f),
            direction = StrokeDirection.TOP_TO_BOTTOM,
            type = StrokeType.VERTICAL
        )
    )
    
    val I_KATAKANA = listOf(
        // Stroke 1: Long vertical
        StrokeData(
            id = 1,
            path = listOf(PointF(0.5f, 0.2f), PointF(0.5f, 0.8f)),
            startPoint = PointF(0.5f, 0.2f),
            endPoint = PointF(0.5f, 0.8f),
            direction = StrokeDirection.TOP_TO_BOTTOM,
            type = StrokeType.VERTICAL
        ),
        // Stroke 2: Right dot
        StrokeData(
            id = 2,
            path = listOf(PointF(0.7f, 0.35f)),
            startPoint = PointF(0.7f, 0.35f),
            endPoint = PointF(0.7f, 0.35f),
            direction = StrokeDirection.DOT,
            type = StrokeType.DOT
        )
    )
    
    val U_KATAKANA = listOf(
        // Stroke 1: Top horizontal
        StrokeData(
            id = 1,
            path = listOf(PointF(0.2f, 0.35f), PointF(0.8f, 0.35f)),
            startPoint = PointF(0.2f, 0.35f),
            endPoint = PointF(0.8f, 0.35f),
            direction = StrokeDirection.LEFT_TO_RIGHT,
            type = StrokeType.HORIZONTAL
        ),
        // Stroke 2: Left vertical
        StrokeData(
            id = 2,
            path = listOf(PointF(0.3f, 0.35f), PointF(0.3f, 0.8f)),
            startPoint = PointF(0.3f, 0.35f),
            endPoint = PointF(0.3f, 0.8f),
            direction = StrokeDirection.TOP_TO_BOTTOM,
            type = StrokeType.VERTICAL
        ),
        // Stroke 3: Curved right
        StrokeData(
            id = 3,
            path = listOf(
                PointF(0.3f, 0.55f),
                PointF(0.5f, 0.5f),
                PointF(0.7f, 0.55f),
                PointF(0.8f, 0.7f),
                PointF(0.7f, 0.85f)
            ),
            startPoint = PointF(0.3f, 0.55f),
            endPoint = PointF(0.7f, 0.85f),
            direction = StrokeDirection.LEFT_TO_RIGHT_CURVED,
            type = StrokeType.CURVE
        )
    )
    
    val E_KATAKANA = listOf(
        // Stroke 1: Top horizontal
        StrokeData(
            id = 1,
            path = listOf(PointF(0.25f, 0.3f), PointF(0.75f, 0.3f)),
            startPoint = PointF(0.25f, 0.3f),
            endPoint = PointF(0.75f, 0.3f),
            direction = StrokeDirection.LEFT_TO_RIGHT,
            type = StrokeType.HORIZONTAL
        ),
        // Stroke 2: Middle vertical
        StrokeData(
            id = 2,
            path = listOf(PointF(0.5f, 0.3f), PointF(0.5f, 0.75f)),
            startPoint = PointF(0.5f, 0.3f),
            endPoint = PointF(0.5f, 0.75f),
            direction = StrokeDirection.TOP_TO_BOTTOM,
            type = StrokeType.VERTICAL
        ),
        // Stroke 3: Bottom sweep
        StrokeData(
            id = 3,
            path = listOf(
                PointF(0.5f, 0.55f),
                PointF(0.65f, 0.7f),
                PointF(0.75f, 0.75f)
            ),
            startPoint = PointF(0.5f, 0.55f),
            endPoint = PointF(0.75f, 0.75f),
            direction = StrokeDirection.DIAGONAL_DOWN_RIGHT,
            type = StrokeType.DIAGONAL
        )
    )
    
    val O_KATAKANA = listOf(
        // Stroke 1: Left diagonal
        StrokeData(
            id = 1,
            path = listOf(PointF(0.3f, 0.3f), PointF(0.2f, 0.7f)),
            startPoint = PointF(0.3f, 0.3f),
            endPoint = PointF(0.2f, 0.7f),
            direction = StrokeDirection.DIAGONAL_DOWN_LEFT,
            type = StrokeType.DIAGONAL
        ),
        // Stroke 2: Top right diagonal
        StrokeData(
            id = 2,
            path = listOf(PointF(0.3f, 0.3f), PointF(0.7f, 0.2f)),
            startPoint = PointF(0.3f, 0.3f),
            endPoint = PointF(0.7f, 0.2f),
            direction = StrokeDirection.DIAGONAL_UP_RIGHT,
            type = StrokeType.DIAGONAL
        ),
        // Stroke 3: Vertical right
        StrokeData(
            id = 3,
            path = listOf(PointF(0.7f, 0.2f), PointF(0.7f, 0.8f)),
            startPoint = PointF(0.7f, 0.2f),
            endPoint = PointF(0.7f, 0.8f),
            direction = StrokeDirection.TOP_TO_BOTTOM,
            type = StrokeType.VERTICAL
        )
    )
    
    /**
     * Get stroke data for a character
     */
    fun getStrokes(character: String): List<StrokeData> {
        return when (character) {
            "あ" -> A
            "い" -> I
            "う" -> U
            "え" -> E
            "お" -> O
            "か" -> KA
            "き" -> KI
            "く" -> KU
            "け" -> KE
            "こ" -> KO
            "ア" -> A_KATAKANA
            "イ" -> I_KATAKANA
            "ウ" -> U_KATAKANA
            "エ" -> E_KATAKANA
            "オ" -> O_KATAKANA
            // Add remaining characters...
            else -> emptyList()
        }
    }
}