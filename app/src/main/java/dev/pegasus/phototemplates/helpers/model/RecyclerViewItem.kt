package dev.pegasus.phototemplates.helpers.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecyclerViewItem(
    val id: Int,
    val imageId: Int,
    val featureImageId: Int = 0,
    val title: String = "",
    val type: String = "",
    @JvmField
    var selector: Boolean = false,
) : Parcelable