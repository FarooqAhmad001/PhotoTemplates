package dev.pegasus.template.helpers.dataClasses

import androidx.annotation.DrawableRes

data class TemplateMainTabsModel(
    val id: Int = 0,
    @DrawableRes val iconResourceId: Int = 0,
    val tabName: String = "",
    var isSelected: Boolean = false
)
