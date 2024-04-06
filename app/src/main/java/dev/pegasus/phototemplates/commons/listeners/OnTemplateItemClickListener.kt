package dev.pegasus.phototemplates.commons.listeners

import dev.pegasus.template.helpers.dataClasses.TemplateModel

interface OnTemplateItemClickListener {
    fun onItemClick(model: TemplateModel)
}