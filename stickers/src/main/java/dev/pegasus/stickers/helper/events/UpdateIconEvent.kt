package dev.pegasus.stickers.helper.events

import android.view.MotionEvent
import dev.pegasus.stickers.StickerView
import dev.pegasus.stickers.ui.StickerIconEvent

class UpdateIconEvent: StickerIconEvent {
    override fun onActionDown(stickerView: StickerView?, event: MotionEvent?) {}

    override fun onActionMove(stickerView: StickerView?, event: MotionEvent?) {}

    override fun onActionUp(stickerView: StickerView?, event: MotionEvent?) {
        stickerView?.updateSticker()
    }
}