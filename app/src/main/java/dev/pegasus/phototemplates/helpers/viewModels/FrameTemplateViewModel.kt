package dev.pegasus.phototemplates.helpers.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import dev.pegasus.stickers.helper.Sticker

class FrameTemplateViewModel(): ViewModel() {

    /* --------------------------------------------- Collage - Text --------------------------------------------- */

    private var sticker: Sticker? = null
    private var textFont: Int = 0
    private var textColor: Int = 0

    var fontStyle: Int
        get() = textFont
        set(value) {
            textFont = value
        }

    var fontColor: Int
        get() = textColor
        set(value) {
            textColor = value
        }

    fun getSticker(): Sticker? {
        return sticker
    }

    fun saveCustomizedText(sticker: Sticker?) {
        this.sticker = sticker
    }

    fun clearCustomizedText() {
        this.sticker = null
    }

    private fun resetTextStyles() {
        sticker = null
        fontStyle = 0
        fontColor = 0
    }

}