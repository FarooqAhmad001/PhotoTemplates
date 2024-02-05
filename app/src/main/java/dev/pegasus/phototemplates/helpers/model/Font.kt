package dev.pegasus.phototemplates.helpers.model

data class Font(
    val id: Int,
    val title: String,
    val fontType: String,
    var selector: Boolean = false,
)
