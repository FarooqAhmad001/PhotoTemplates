package dev.pegasus.template.helpers.dataClasses

import android.graphics.fonts.FontFamily
import android.text.Layout
import androidx.annotation.DrawableRes

data class StickerItem (
    val id: Int,
    val stickerType: StickerType? = null,
    @DrawableRes
    val imageId: Int? = null,
    val width: Float,
    val height: Float,
    val xAxis: Float,
    val yAxis: Float,
    val content: String?,
    val font: Int? = null,
    val weight: Float,
    val size: Float,
    val alignment: Layout.Alignment = Layout.Alignment.ALIGN_CENTER,
    val lineHeight: Float,
    val color: String?,
)