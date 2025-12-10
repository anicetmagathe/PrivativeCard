package core.model.entity

import android.graphics.Color
import androidx.annotation.ColorInt

data class Theme(
    @param:ColorInt val backgroundColor: Int = Color.WHITE,
    @param:ColorInt val foregroundColor: Int = Color.BLACK
)
