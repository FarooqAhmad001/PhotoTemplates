package dev.pegasus.template.helpers.state

import android.os.Parcel
import android.os.Parcelable
import android.view.View.BaseSavedState

class CustomViewState(superState: Parcelable?) : BaseSavedState(superState) {
    var photoAspectRatio: Float = 0f
    var zoomFactor: Float = 1f
    var centerX: Float = 0f
    var centerY: Float = 0f
    var dx: Float = 0f
    var dy: Float = 0f
    var rotationAngle: Float = 0f

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeFloat(photoAspectRatio ?: 0f)
        out.writeFloat(zoomFactor ?: 1f)
        out.writeFloat(centerX ?: 0f)
        out.writeFloat(centerY ?: 0f)
        out.writeFloat(dx ?: 0f)
        out.writeFloat(dy ?: 0f)
        out.writeFloat(rotationAngle ?: 0f)
    }

}
