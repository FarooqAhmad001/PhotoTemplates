package dev.pegasus.phototemplates.helpers.extensions

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.textview.MaterialTextView

fun MaterialTextView.changeColor(@ColorRes color: Int) {
    val textColor = ContextCompat.getColor(this.context, color)
    setTextColor(textColor)

    val colorFilter = PorterDuffColorFilter(textColor, PorterDuff.Mode.SRC_IN)
    compoundDrawables.forEach { drawable ->
        drawable?.let {
            val temp = it.mutate()
            temp.colorFilter = colorFilter
            setCompoundDrawablesWithIntrinsicBounds(null, temp, null, null)
        }
    }
}