package dev.pegasus.template.helpers.dataProviders

import dev.pegasus.template.R
import dev.pegasus.template.helpers.dataClasses.RecycleViewModelClass
import dev.pegasus.template.helpers.dataClasses.TemplateMainTabsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DpTemplateTabs {

    val list = listOf(
        TemplateMainTabsModel(id = 0, iconResourceId = R.drawable.ic_template, tabName = "Template", isSelected = true),
        TemplateMainTabsModel(id = 1, iconResourceId = R.drawable.ic_filter, tabName = "Filter", isSelected = false),
        TemplateMainTabsModel(id = 2, iconResourceId = R.drawable.ic_adjust, tabName = "Adjust", isSelected = false),
        TemplateMainTabsModel(id = 3, iconResourceId = R.drawable.ic_text, tabName = "Text", isSelected = false),
        TemplateMainTabsModel(id = 4, iconResourceId = R.drawable.ic_sticker, tabName = "Sticker", isSelected = false),
        TemplateMainTabsModel(id = 4, iconResourceId = R.drawable.ic_tab_draw, tabName = "Draw", isSelected = false),
    )

    suspend fun getFiltersList(): List<RecycleViewModelClass> {
        return withContext(Dispatchers.Default) {
            arrayListOf(
                RecycleViewModelClass(id = 0, controlIcon = R.drawable.img_template_anniversary_thumbnail_1, controlName = "Base", isSelected = true),
                RecycleViewModelClass(id = 1, controlIcon = R.drawable.img_template_anniversary_thumbnail_1, controlName = "V1", isSelected = false),
                RecycleViewModelClass(id = 2, controlIcon = R.drawable.img_template_anniversary_thumbnail_1, controlName = "V2", isSelected = false),
                RecycleViewModelClass(id = 3, controlIcon = R.drawable.img_template_anniversary_thumbnail_1, controlName = "V3", isSelected = false),
                RecycleViewModelClass(id = 4, controlIcon = R.drawable.img_template_anniversary_thumbnail_1, controlName = "V4", isSelected = false),
                RecycleViewModelClass(id = 5, controlIcon = R.drawable.img_template_anniversary_thumbnail_1, controlName = "V5", isSelected = false),
                RecycleViewModelClass(id = 6, controlIcon = R.drawable.img_template_anniversary_thumbnail_1, controlName = "V6", isSelected = false),
            )
        } 
    }

    suspend fun getAdjustControlsList(): List<RecycleViewModelClass> {
        return withContext(Dispatchers.Default) {
            listOf(
                RecycleViewModelClass(id = 0, controlIcon = R.drawable.ic_brightness, controlName = "Brightness", isSelected = true),
                RecycleViewModelClass(id = 1, controlIcon = R.drawable.ic_contrast, controlName = "Contrast", isSelected = false),
                RecycleViewModelClass(id = 2, controlIcon = R.drawable.ic_sharpen, controlName = "Sharpen", isSelected = false),
                RecycleViewModelClass(id = 3, controlIcon = R.drawable.ic_saturation, controlName = "Saturation", isSelected = false),
                RecycleViewModelClass(id = 4, controlIcon = R.drawable.ic_warmth, controlName = "Warmth", isSelected = false),
                RecycleViewModelClass(id = 5, controlIcon = R.drawable.ic_shadow, controlName = "Shadow", isSelected = false),
                RecycleViewModelClass(id = 6, controlIcon = R.drawable.ic_highlight, controlName = "Highlight", isSelected = false),
                RecycleViewModelClass(id = 7, controlIcon = R.drawable.ic_vegnette, controlName = "Vignette", isSelected = false),
                RecycleViewModelClass(id = 8, controlIcon = R.drawable.ic_hue, controlName = "Hue", isSelected = false),
                RecycleViewModelClass(id = 9, controlIcon = R.drawable.ic_grain, controlName = "Grain", isSelected = false),
                RecycleViewModelClass(id = 10, controlIcon = R.drawable.ic_fade, controlName = "Fade", isSelected = false),
                RecycleViewModelClass(id = 11, controlIcon = R.drawable.ic_tint, controlName = "Tint", isSelected = false),
            )
        }
    }

}