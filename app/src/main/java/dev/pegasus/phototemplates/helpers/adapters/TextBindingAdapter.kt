package dev.pegasus.phototemplates.helpers.adapters

import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView
import dev.pegasus.phototemplates.R
import dev.pegasus.phototemplates.helpers.binding_adapters.setImage
import dev.pegasus.phototemplates.helpers.extensions.changeColor
import dev.pegasus.phototemplates.helpers.managers.TextManager

/**
 * @param case
 *     Case 0: Normal   -> Image & Text tint
 *     Case 1: Selector -> White Border
 */


@BindingAdapter("drawableTopId", "selector")
fun MaterialTextView.setDrawableTop(drawableTopId: Int, selector: Boolean) {
    this.setCompoundDrawablesWithIntrinsicBounds(0, drawableTopId, 0, 0)
    if (selector)
        this.changeColor(R.color.white)
    else
        this.changeColor(R.color.text_color)
}

@BindingAdapter("customTypeface")
fun TextView.setTypeface(fontType: String) {
    val textManager = TextManager(this.context)
    textManager.applyFont(fontType, this)
}
