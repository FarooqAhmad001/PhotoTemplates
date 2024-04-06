package dev.pegasus.template.helpers.dataClasses

import androidx.annotation.DrawableRes

data class RecycleViewModelClass(
    val id: Int = 0,
    @DrawableRes val controlIcon: Int = 0,
    val controlName: String = "",
    var isSelected: Boolean = false
)
