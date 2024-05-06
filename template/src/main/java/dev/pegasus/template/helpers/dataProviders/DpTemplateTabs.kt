package dev.pegasus.template.helpers.dataProviders

import dev.pegasus.template.R
import dev.pegasus.template.helpers.dataClasses.FontModel
import dev.pegasus.template.helpers.dataClasses.MaterialColorModel
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

    suspend fun getTextTabList(selectedTabPosition: Int): List<RecycleViewModelClass> {
        return withContext(Dispatchers.Default) {
            val list = listOf(
                RecycleViewModelClass(id = 0, controlIcon = R.drawable.ic_keyboard, controlName = "Keyboard", isSelected = false),
                RecycleViewModelClass(id = 1, controlIcon = R.drawable.ic_font, controlName = "Font", isSelected = false),
                RecycleViewModelClass(id = 2, controlIcon = R.drawable.ic_style, controlName = "Style", isSelected = false),
                RecycleViewModelClass(id = 3, controlIcon = R.drawable.ic_color, controlName = "Color", isSelected = false),
            )
            list[selectedTabPosition].isSelected = true
            return@withContext list
        }
    }

    suspend fun getFontsList(position: Int): List<FontModel> {
        return withContext(Dispatchers.Default) {
            val list = listOf(
                FontModel(id = 0, name = "Arial", isSelected = false),
                FontModel(id = 1, name = "Times New Roman", isSelected = false),
                FontModel(id = 2, name = "Calibre", isSelected = false),
                FontModel(id = 3, name = "Cardamon", isSelected = false),
                FontModel(id = 3, name = "Georgia", isSelected = false),
                FontModel(id = 3, name = "Verdana", isSelected = false),
            )
            list[position].isSelected = true

            return@withContext list
        }
    }

    suspend fun getMaterialColorsList(position: Int): List<MaterialColorModel> {
        return withContext(Dispatchers.Default) {
            val materialColors = listOf(
                // Red colors
                MaterialColorModel(colorGroup = "Red", colorName = "Red 50", colorCode = "#FFEBEE"),
                MaterialColorModel(colorGroup = "Red", colorName = "Red 100", colorCode = "#FFCDD2"),
                MaterialColorModel(colorGroup = "Red", colorName = "Red 200", colorCode = "#EF9A9A"),
                MaterialColorModel(colorGroup = "Red", colorName = "Red 300", colorCode = "#E57373"),
                MaterialColorModel(colorGroup = "Red", colorName = "Red 400", colorCode = "#EF5350"),
                MaterialColorModel(colorGroup = "Red", colorName = "Red 500", colorCode = "#F44336"),
                MaterialColorModel(colorGroup = "Red", colorName = "Red 600", colorCode = "#E53935"),
                MaterialColorModel(colorGroup = "Red", colorName = "Red 700", colorCode = "#D32F2F"),
                MaterialColorModel(colorGroup = "Red", colorName = "Red 800", colorCode = "#C62828"),
                MaterialColorModel(colorGroup = "Red", colorName = "Red 900", colorCode = "#B71C1C"),
                MaterialColorModel(colorGroup = "Red", colorName = "Red A100", colorCode = "#FF8A80"),
                MaterialColorModel(colorGroup = "Red", colorName = "Red A200", colorCode = "#FF5252"),
                MaterialColorModel(colorGroup = "Red", colorName = "Red A400", colorCode = "#FF1744"),
                MaterialColorModel(colorGroup = "Red", colorName = "Red A700", colorCode = "#D50000"),

                // Pink colors
                MaterialColorModel(colorGroup = "Pink", colorName = "Pink 50", colorCode = "#FCE4EC"),
                MaterialColorModel(colorGroup = "Pink", colorName = "Pink 100", colorCode = "#F8BBD0"),
                MaterialColorModel(colorGroup = "Pink", colorName = "Pink 200", colorCode = "#F48FB1"),
                MaterialColorModel(colorGroup = "Pink", colorName = "Pink 300", colorCode = "#F06292"),
                MaterialColorModel(colorGroup = "Pink", colorName = "Pink 400", colorCode = "#EC407A"),
                MaterialColorModel(colorGroup = "Pink", colorName = "Pink 500", colorCode = "#E91E63"),
                MaterialColorModel(colorGroup = "Pink", colorName = "Pink 600", colorCode = "#D81B60"),
                MaterialColorModel(colorGroup = "Pink", colorName = "Pink 700", colorCode = "#C2185B"),
                MaterialColorModel(colorGroup = "Pink", colorName = "Pink 800", colorCode = "#AD1457"),
                MaterialColorModel(colorGroup = "Pink", colorName = "Pink 900", colorCode = "#880E4F"),
                MaterialColorModel(colorGroup = "Pink", colorName = "Pink A100", colorCode = "#FF80AB"),
                MaterialColorModel(colorGroup = "Pink", colorName = "Pink A200", colorCode = "#FF4081"),
                MaterialColorModel(colorGroup = "Pink", colorName = "Pink A400", colorCode = "#FF1744"),
                MaterialColorModel(colorGroup = "Pink", colorName = "Pink A700", colorCode = "#D50000"),

                // Purple colors
                MaterialColorModel(colorGroup = "Purple", colorName = "Purple 50", colorCode = "#F3E5F5"),
                MaterialColorModel(colorGroup = "Purple", colorName = "Purple 100", colorCode = "#E1BEE7"),
                MaterialColorModel(colorGroup = "Purple", colorName = "Purple 200", colorCode = "#CE93D8"),
                MaterialColorModel(colorGroup = "Purple", colorName = "Purple 300", colorCode = "#BA68C8"),
                MaterialColorModel(colorGroup = "Purple", colorName = "Purple 400", colorCode = "#AB47BC"),
                MaterialColorModel(colorGroup = "Purple", colorName = "Purple 500", colorCode = "#9C27B0"),
                MaterialColorModel(colorGroup = "Purple", colorName = "Purple 600", colorCode = "#8E24AA"),
                MaterialColorModel(colorGroup = "Purple", colorName = "Purple 700", colorCode = "#7B1FA2"),
                MaterialColorModel(colorGroup = "Purple", colorName = "Purple 800", colorCode = "#6A1B9A"),
                MaterialColorModel(colorGroup = "Purple", colorName = "Purple 900", colorCode = "#4A148C"),
                MaterialColorModel(colorGroup = "Purple", colorName = "Purple A100", colorCode = "#EA80FC"),
                MaterialColorModel(colorGroup = "Purple", colorName = "Purple A200", colorCode = "#E040FB"),
                MaterialColorModel(colorGroup = "Purple", colorName = "Purple A400", colorCode = "#D500F9"),
                MaterialColorModel(colorGroup = "Purple", colorName = "Purple A700", colorCode = "#AA00FF"),

                // Deep Purple colors
                MaterialColorModel(colorGroup = "Deep Purple", colorName = "Deep Purple 50", colorCode = "#EDE7F6"),
                MaterialColorModel(colorGroup = "Deep Purple", colorName = "Deep Purple 100", colorCode = "#D1C4E9"),
                MaterialColorModel(colorGroup = "Deep Purple", colorName = "Deep Purple 200", colorCode = "#B39DDB"),
                MaterialColorModel(colorGroup = "Deep Purple", colorName = "Deep Purple 300", colorCode = "#9575CD"),
                MaterialColorModel(colorGroup = "Deep Purple", colorName = "Deep Purple 400", colorCode = "#7E57C2"),
                MaterialColorModel(colorGroup = "Deep Purple", colorName = "Deep Purple 500", colorCode = "#673AB7"),
                MaterialColorModel(colorGroup = "Deep Purple", colorName = "Deep Purple 600", colorCode = "#5E35B1"),
                MaterialColorModel(colorGroup = "Deep Purple", colorName = "Deep Purple 700", colorCode = "#512DA8"),
                MaterialColorModel(colorGroup = "Deep Purple", colorName = "Deep Purple 800", colorCode = "#4527A0"),
                MaterialColorModel(colorGroup = "Deep Purple", colorName = "Deep Purple 900", colorCode = "#311B92"),
                MaterialColorModel(colorGroup = "Deep Purple", colorName = "Deep Purple A100", colorCode = "#B388FF"),
                MaterialColorModel(colorGroup = "Deep Purple", colorName = "Deep Purple A200", colorCode = "#7C4DFF"),
                MaterialColorModel(colorGroup = "Deep Purple", colorName = "Deep Purple A400", colorCode = "#651FFF"),
                MaterialColorModel(colorGroup = "Deep Purple", colorName = "Deep Purple A700", colorCode = "#6200EA"),

                // Indigo colors
                MaterialColorModel(colorGroup = "Indigo", colorName = "Indigo 50", colorCode = "#E8EAF6"),
                MaterialColorModel(colorGroup = "Indigo", colorName = "Indigo 100", colorCode = "#C5CAE9"),
                MaterialColorModel(colorGroup = "Indigo", colorName = "Indigo 200", colorCode = "#9FA8DA"),
                MaterialColorModel(colorGroup = "Indigo", colorName = "Indigo 300", colorCode = "#7986CB"),
                MaterialColorModel(colorGroup = "Indigo", colorName = "Indigo 400", colorCode = "#5C6BC0"),
                MaterialColorModel(colorGroup = "Indigo", colorName = "Indigo 500", colorCode = "#3F51B5"),
                MaterialColorModel(colorGroup = "Indigo", colorName = "Indigo 600", colorCode = "#3949AB"),
                MaterialColorModel(colorGroup = "Indigo", colorName = "Indigo 700", colorCode = "#303F9F"),
                MaterialColorModel(colorGroup = "Indigo", colorName = "Indigo 800", colorCode = "#283593"),
                MaterialColorModel(colorGroup = "Indigo", colorName = "Indigo 900", colorCode = "#1A237E"),
                MaterialColorModel(colorGroup = "Indigo", colorName = "Indigo A100", colorCode = "#8C9EFF"),
                MaterialColorModel(colorGroup = "Indigo", colorName = "Indigo A200", colorCode = "#536DFE"),
                MaterialColorModel(colorGroup = "Indigo", colorName = "Indigo A400", colorCode = "#3D5AFE"),
                MaterialColorModel(colorGroup = "Indigo", colorName = "Indigo A700", colorCode = "#304FFE"),

                // Blue colors
                MaterialColorModel(colorGroup = "Blue", colorName = "Blue 50", colorCode = "#E3F2FD"),
                MaterialColorModel(colorGroup = "Blue", colorName = "Blue 100", colorCode = "#BBDEFB"),
                MaterialColorModel(colorGroup = "Blue", colorName = "Blue 200", colorCode = "#90CAF9"),
                MaterialColorModel(colorGroup = "Blue", colorName = "Blue 300", colorCode = "#64B5F6"),
                MaterialColorModel(colorGroup = "Blue", colorName = "Blue 400", colorCode = "#42A5F5"),
                MaterialColorModel(colorGroup = "Blue", colorName = "Blue 500", colorCode = "#2196F3"),
                MaterialColorModel(colorGroup = "Blue", colorName = "Blue 600", colorCode = "#1E88E5"),
                MaterialColorModel(colorGroup = "Blue", colorName = "Blue 700", colorCode = "#1976D2"),
                MaterialColorModel(colorGroup = "Blue", colorName = "Blue 800", colorCode = "#1565C0"),
                MaterialColorModel(colorGroup = "Blue", colorName = "Blue 900", colorCode = "#0D47A1"),
                MaterialColorModel(colorGroup = "Blue", colorName = "Blue A100", colorCode = "#82B1FF"),
                MaterialColorModel(colorGroup = "Blue", colorName = "Blue A200", colorCode = "#448AFF"),
                MaterialColorModel(colorGroup = "Blue", colorName = "Blue A400", colorCode = "#2979FF"),
                MaterialColorModel(colorGroup = "Blue", colorName = "Blue A700", colorCode = "#2962FF"),

                // Light Blue colors
                MaterialColorModel(colorGroup = "Light Blue", colorName = "Light Blue 50", colorCode = "#E1F5FE"),
                MaterialColorModel(colorGroup = "Light Blue", colorName = "Light Blue 100", colorCode = "#B3E5FC"),
                MaterialColorModel(colorGroup = "Light Blue", colorName = "Light Blue 200", colorCode = "#81D4FA"),
                MaterialColorModel(colorGroup = "Light Blue", colorName = "Light Blue 300", colorCode = "#4FC3F7"),
                MaterialColorModel(colorGroup = "Light Blue", colorName = "Light Blue 400", colorCode = "#29B6F6"),
                MaterialColorModel(colorGroup = "Light Blue", colorName = "Light Blue 500", colorCode = "#03A9F4"),
                MaterialColorModel(colorGroup = "Light Blue", colorName = "Light Blue 600", colorCode = "#039BE5"),
                MaterialColorModel(colorGroup = "Light Blue", colorName = "Light Blue 700", colorCode = "#0288D1"),
                MaterialColorModel(colorGroup = "Light Blue", colorName = "Light Blue 800", colorCode = "#0277BD"),
                MaterialColorModel(colorGroup = "Light Blue", colorName = "Light Blue 900", colorCode = "#01579B"),
                MaterialColorModel(colorGroup = "Light Blue", colorName = "Light Blue A100", colorCode = "#80D8FF"),
                MaterialColorModel(colorGroup = "Light Blue", colorName = "Light Blue A200", colorCode = "#40C4FF"),
                MaterialColorModel(colorGroup = "Light Blue", colorName = "Light Blue A400", colorCode = "#00B0FF"),
                MaterialColorModel(colorGroup = "Light Blue", colorName = "Light Blue A700", colorCode = "#0091EA"),

                // Cyan colors
                MaterialColorModel(colorGroup = "Cyan", colorName = "Cyan 50", colorCode = "#E0F7FA"),
                MaterialColorModel(colorGroup = "Cyan", colorName = "Cyan 100", colorCode = "#B2EBF2"),
                MaterialColorModel(colorGroup = "Cyan", colorName = "Cyan 200", colorCode = "#80DEEA"),
                MaterialColorModel(colorGroup = "Cyan", colorName = "Cyan 300", colorCode = "#4DD0E1"),
                MaterialColorModel(colorGroup = "Cyan", colorName = "Cyan 400", colorCode = "#26C6DA"),
                MaterialColorModel(colorGroup = "Cyan", colorName = "Cyan 500", colorCode = "#00BCD4"),
                MaterialColorModel(colorGroup = "Cyan", colorName = "Cyan 600", colorCode = "#00ACC1"),
                MaterialColorModel(colorGroup = "Cyan", colorName = "Cyan 700", colorCode = "#0097A7"),
                MaterialColorModel(colorGroup = "Cyan", colorName = "Cyan 800", colorCode = "#00838F"),
                MaterialColorModel(colorGroup = "Cyan", colorName = "Cyan 900", colorCode = "#006064"),
                MaterialColorModel(colorGroup = "Cyan", colorName = "Cyan A100", colorCode = "#84FFFF"),
                MaterialColorModel(colorGroup = "Cyan", colorName = "Cyan A200", colorCode = "#18FFFF"),
                MaterialColorModel(colorGroup = "Cyan", colorName = "Cyan A400", colorCode = "#00E5FF"),
                MaterialColorModel(colorGroup = "Cyan", colorName = "Cyan A700", colorCode = "#00B8D4"),

                // Teal colors
                MaterialColorModel(colorGroup = "Teal", colorName = "Teal 50", colorCode = "#E0F2F1"),
                MaterialColorModel(colorGroup = "Teal", colorName = "Teal 100", colorCode = "#B2DFDB"),
                MaterialColorModel(colorGroup = "Teal", colorName = "Teal 200", colorCode = "#80CBC4"),
                MaterialColorModel(colorGroup = "Teal", colorName = "Teal 300", colorCode = "#4DB6AC"),
                MaterialColorModel(colorGroup = "Teal", colorName = "Teal 400", colorCode = "#26A69A"),
                MaterialColorModel(colorGroup = "Teal", colorName = "Teal 500", colorCode = "#009688"),
                MaterialColorModel(colorGroup = "Teal", colorName = "Teal 600", colorCode = "#00897B"),
                MaterialColorModel(colorGroup = "Teal", colorName = "Teal 700", colorCode = "#00796B"),
                MaterialColorModel(colorGroup = "Teal", colorName = "Teal 800", colorCode = "#00695C"),
                MaterialColorModel(colorGroup = "Teal", colorName = "Teal 900", colorCode = "#004D40"),
                MaterialColorModel(colorGroup = "Teal", colorName = "Teal A100", colorCode = "#A7FFEB"),
                MaterialColorModel(colorGroup = "Teal", colorName = "Teal A200", colorCode = "#64FFDA"),
                MaterialColorModel(colorGroup = "Teal", colorName = "Teal A400", colorCode = "#1DE9B6"),
                MaterialColorModel(colorGroup = "Teal", colorName = "Teal A700", colorCode = "#00BFA5"),

                // Green colors
                MaterialColorModel(colorGroup = "Green", colorName = "Green 50", colorCode = "#E8F5E9"),
                MaterialColorModel(colorGroup = "Green", colorName = "Green 100", colorCode = "#C8E6C9"),
                MaterialColorModel(colorGroup = "Green", colorName = "Green 200", colorCode = "#A5D6A7"),
                MaterialColorModel(colorGroup = "Green", colorName = "Green 300", colorCode = "#81C784"),
                MaterialColorModel(colorGroup = "Green", colorName = "Green 400", colorCode = "#66BB6A"),
                MaterialColorModel(colorGroup = "Green", colorName = "Green 500", colorCode = "#4CAF50"),
                MaterialColorModel(colorGroup = "Green", colorName = "Green 600", colorCode = "#43A047"),
                MaterialColorModel(colorGroup = "Green", colorName = "Green 700", colorCode = "#388E3C"),
                MaterialColorModel(colorGroup = "Green", colorName = "Green 800", colorCode = "#2E7D32"),
                MaterialColorModel(colorGroup = "Green", colorName = "Green 900", colorCode = "#1B5E20"),
                MaterialColorModel(colorGroup = "Green", colorName = "Green A100", colorCode = "#B9F6CA"),
                MaterialColorModel(colorGroup = "Green", colorName = "Green A200", colorCode = "#69F0AE"),
                MaterialColorModel(colorGroup = "Green", colorName = "Green A400", colorCode = "#00E676"),
                MaterialColorModel(colorGroup = "Green", colorName = "Green A700", colorCode = "#00C853"),

                // Light Green colors
                MaterialColorModel(colorGroup = "Light Green", colorName = "Light Green 50", colorCode = "#F1F8E9"),
                MaterialColorModel(colorGroup = "Light Green", colorName = "Light Green 100", colorCode = "#DCEDC8"),
                MaterialColorModel(colorGroup = "Light Green", colorName = "Light Green 200", colorCode = "#C5E1A5"),
                MaterialColorModel(colorGroup = "Light Green", colorName = "Light Green 300", colorCode = "#AED581"),
                MaterialColorModel(colorGroup = "Light Green", colorName = "Light Green 400", colorCode = "#9CCC65"),
                MaterialColorModel(colorGroup = "Light Green", colorName = "Light Green 500", colorCode = "#8BC34A"),
                MaterialColorModel(colorGroup = "Light Green", colorName = "Light Green 600", colorCode = "#7CB342"),
                MaterialColorModel(colorGroup = "Light Green", colorName = "Light Green 700", colorCode = "#689F38"),
                MaterialColorModel(colorGroup = "Light Green", colorName = "Light Green 800", colorCode = "#558B2F"),
                MaterialColorModel(colorGroup = "Light Green", colorName = "Light Green 900", colorCode = "#33691E"),
                MaterialColorModel(colorGroup = "Light Green", colorName = "Light Green A100", colorCode = "#CCFF90"),
                MaterialColorModel(colorGroup = "Light Green", colorName = "Light Green A200", colorCode = "#B2FF59"),
                MaterialColorModel(colorGroup = "Light Green", colorName = "Light Green A400", colorCode = "#76FF03"),
                MaterialColorModel(colorGroup = "Light Green", colorName = "Light Green A700", colorCode = "#64DD17"),

                // Lime colors
                MaterialColorModel(colorGroup = "Lime", colorName = "Lime 50", colorCode = "#F9FBE7"),
                MaterialColorModel(colorGroup = "Lime", colorName = "Lime 100", colorCode = "#F0F4C3"),
                MaterialColorModel(colorGroup = "Lime", colorName = "Lime 200", colorCode = "#E6EE9C"),
                MaterialColorModel(colorGroup = "Lime", colorName = "Lime 300", colorCode = "#DCE775"),
                MaterialColorModel(colorGroup = "Lime", colorName = "Lime 400", colorCode = "#D4E157"),
                MaterialColorModel(colorGroup = "Lime", colorName = "Lime 500", colorCode = "#CDDC39"),
                MaterialColorModel(colorGroup = "Lime", colorName = "Lime 600", colorCode = "#C0CA33"),
                MaterialColorModel(colorGroup = "Lime", colorName = "Lime 700", colorCode = "#AFB42B"),
                MaterialColorModel(colorGroup = "Lime", colorName = "Lime 800", colorCode = "#9E9D24"),
                MaterialColorModel(colorGroup = "Lime", colorName = "Lime 900", colorCode = "#827717"),
                MaterialColorModel(colorGroup = "Lime", colorName = "Lime A100", colorCode = "#F4FF81"),
                MaterialColorModel(colorGroup = "Lime", colorName = "Lime A200", colorCode = "#EEFF41"),
                MaterialColorModel(colorGroup = "Lime", colorName = "Lime A400", colorCode = "#C6FF00"),
                MaterialColorModel(colorGroup = "Lime", colorName = "Lime A700", colorCode = "#AEEA00"),

                // Yellow colors
                MaterialColorModel(colorGroup = "Yellow", colorName = "Yellow 50", colorCode = "#FFFDE7"),
                MaterialColorModel(colorGroup = "Yellow", colorName = "Yellow 100", colorCode = "#FFF9C4"),
                MaterialColorModel(colorGroup = "Yellow", colorName = "Yellow 200", colorCode = "#FFF59D"),
                MaterialColorModel(colorGroup = "Yellow", colorName = "Yellow 300", colorCode = "#FFF176"),
                MaterialColorModel(colorGroup = "Yellow", colorName = "Yellow 400", colorCode = "#FFEE58"),
                MaterialColorModel(colorGroup = "Yellow", colorName = "Yellow 500", colorCode = "#FFEB3B"),
                MaterialColorModel(colorGroup = "Yellow", colorName = "Yellow 600", colorCode = "#FDD835"),
                MaterialColorModel(colorGroup = "Yellow", colorName = "Yellow 700", colorCode = "#FBC02D"),
                MaterialColorModel(colorGroup = "Yellow", colorName = "Yellow 800", colorCode = "#F9A825"),
                MaterialColorModel(colorGroup = "Yellow", colorName = "Yellow 900", colorCode = "#F57F17"),
                MaterialColorModel(colorGroup = "Yellow", colorName = "Yellow A100", colorCode = "#FFFF8D"),
                MaterialColorModel(colorGroup = "Yellow", colorName = "Yellow A200", colorCode = "#FFFF00"),
                MaterialColorModel(colorGroup = "Yellow", colorName = "Yellow A400", colorCode = "#FFEA00"),
                MaterialColorModel(colorGroup = "Yellow", colorName = "Yellow A700", colorCode = "#FFD600"),

                // Amber colors
                MaterialColorModel(colorGroup = "Amber", colorName = "Amber 50", colorCode = "#FFF8E1"),
                MaterialColorModel(colorGroup = "Amber", colorName = "Amber 100", colorCode = "#FFECB3"),
                MaterialColorModel(colorGroup = "Amber", colorName = "Amber 200", colorCode = "#FFE082"),
                MaterialColorModel(colorGroup = "Amber", colorName = "Amber 300", colorCode = "#FFD54F"),
                MaterialColorModel(colorGroup = "Amber", colorName = "Amber 400", colorCode = "#FFCA28"),
                MaterialColorModel(colorGroup = "Amber", colorName = "Amber 500", colorCode = "#FFC107"),
                MaterialColorModel(colorGroup = "Amber", colorName = "Amber 600", colorCode = "#FFB300"),
                MaterialColorModel(colorGroup = "Amber", colorName = "Amber 700", colorCode = "#FFA000"),
                MaterialColorModel(colorGroup = "Amber", colorName = "Amber 800", colorCode = "#FF8F00"),
                MaterialColorModel(colorGroup = "Amber", colorName = "Amber 900", colorCode = "#FF6F00"),
                MaterialColorModel(colorGroup = "Amber", colorName = "Amber A100", colorCode = "#FFE57F"),
                MaterialColorModel(colorGroup = "Amber", colorName = "Amber A200", colorCode = "#FFD740"),
                MaterialColorModel(colorGroup = "Amber", colorName = "Amber A400", colorCode = "#FFC400"),
                MaterialColorModel(colorGroup = "Amber", colorName = "Amber A700", colorCode = "#FFAB00"),

                // Orange colors
                MaterialColorModel(colorGroup = "Orange", colorName = "Orange 50", colorCode = "#FFF3E0"),
                MaterialColorModel(colorGroup = "Orange", colorName = "Orange 100", colorCode = "#FFE0B2"),
                MaterialColorModel(colorGroup = "Orange", colorName = "Orange 200", colorCode = "#FFCC80"),
                MaterialColorModel(colorGroup = "Orange", colorName = "Orange 300", colorCode = "#FFB74D"),
                MaterialColorModel(colorGroup = "Orange", colorName = "Orange 400", colorCode = "#FFA726"),
                MaterialColorModel(colorGroup = "Orange", colorName = "Orange 500", colorCode = "#FF9800"),
                MaterialColorModel(colorGroup = "Orange", colorName = "Orange 600", colorCode = "#FB8C00"),
                MaterialColorModel(colorGroup = "Orange", colorName = "Orange 700", colorCode = "#F57C00"),
                MaterialColorModel(colorGroup = "Orange", colorName = "Orange 800", colorCode = "#EF6C00"),
                MaterialColorModel(colorGroup = "Orange", colorName = "Orange 900", colorCode = "#E65100"),
                MaterialColorModel(colorGroup = "Orange", colorName = "Orange A100", colorCode = "#FFD180"),
                MaterialColorModel(colorGroup = "Orange", colorName = "Orange A200", colorCode = "#FFAB40"),
                MaterialColorModel(colorGroup = "Orange", colorName = "Orange A400", colorCode = "#FF9100"),
                MaterialColorModel(colorGroup = "Orange", colorName = "Orange A700", colorCode = "#FF6D00"),

                // Deep Orange colors
                MaterialColorModel(colorGroup = "Deep Orange", colorName = "Deep Orange 50", colorCode = "#FBE9E7"),
                MaterialColorModel(colorGroup = "Deep Orange", colorName = "Deep Orange 100", colorCode = "#FFCCBC"),
                MaterialColorModel(colorGroup = "Deep Orange", colorName = "Deep Orange 200", colorCode = "#FFAB91"),
                MaterialColorModel(colorGroup = "Deep Orange", colorName = "Deep Orange 300", colorCode = "#FF8A65"),
                MaterialColorModel(colorGroup = "Deep Orange", colorName = "Deep Orange 400", colorCode = "#FF7043"),
                MaterialColorModel(colorGroup = "Deep Orange", colorName = "Deep Orange 500", colorCode = "#FF5722"),
                MaterialColorModel(colorGroup = "Deep Orange", colorName = "Deep Orange 600", colorCode = "#F4511E"),
                MaterialColorModel(colorGroup = "Deep Orange", colorName = "Deep Orange 700", colorCode = "#E64A19"),
                MaterialColorModel(colorGroup = "Deep Orange", colorName = "Deep Orange 800", colorCode = "#D84315"),
                MaterialColorModel(colorGroup = "Deep Orange", colorName = "Deep Orange 900", colorCode = "#BF360C"),
                MaterialColorModel(colorGroup = "Deep Orange", colorName = "Deep Orange A100", colorCode = "#FF9E80"),
                MaterialColorModel(colorGroup = "Deep Orange", colorName = "Deep Orange A200", colorCode = "#FF6E40"),
                MaterialColorModel(colorGroup = "Deep Orange", colorName = "Deep Orange A400", colorCode = "#FF3D00"),
                MaterialColorModel(colorGroup = "Deep Orange", colorName = "Deep Orange A700", colorCode = "#DD2C00"),

                // Brown colors
                MaterialColorModel(colorGroup = "Brown", colorName = "Brown 50", colorCode = "#EFEBE9"),
                MaterialColorModel(colorGroup = "Brown", colorName = "Brown 100", colorCode = "#D7CCC8"),
                MaterialColorModel(colorGroup = "Brown", colorName = "Brown 200", colorCode = "#BCAAA4"),
                MaterialColorModel(colorGroup = "Brown", colorName = "Brown 300", colorCode = "#A1887F"),
                MaterialColorModel(colorGroup = "Brown", colorName = "Brown 400", colorCode = "#8D6E63"),
                MaterialColorModel(colorGroup = "Brown", colorName = "Brown 500", colorCode = "#795548"),
                MaterialColorModel(colorGroup = "Brown", colorName = "Brown 600", colorCode = "#6D4C41"),
                MaterialColorModel(colorGroup = "Brown", colorName = "Brown 700", colorCode = "#5D4037"),
                MaterialColorModel(colorGroup = "Brown", colorName = "Brown 800", colorCode = "#4E342E"),
                MaterialColorModel(colorGroup = "Brown", colorName = "Brown 900", colorCode = "#3E2723"),

                // Gray colors
                MaterialColorModel(colorGroup = "Gray", colorName = "Gray 50", colorCode = "#FAFAFA"),
                MaterialColorModel(colorGroup = "Gray", colorName = "Gray 100", colorCode = "#F5F5F5"),
                MaterialColorModel(colorGroup = "Gray", colorName = "Gray 200", colorCode = "#EEEEEE"),
                MaterialColorModel(colorGroup = "Gray", colorName = "Gray 300", colorCode = "#E0E0E0"),
                MaterialColorModel(colorGroup = "Gray", colorName = "Gray 400", colorCode = "#BDBDBD"),
                MaterialColorModel(colorGroup = "Gray", colorName = "Gray 500", colorCode = "#9E9E9E"),
                MaterialColorModel(colorGroup = "Gray", colorName = "Gray 600", colorCode = "#757575"),
                MaterialColorModel(colorGroup = "Gray", colorName = "Gray 700", colorCode = "#616161"),
                MaterialColorModel(colorGroup = "Gray", colorName = "Gray 800", colorCode = "#424242"),
                MaterialColorModel(colorGroup = "Gray", colorName = "Gray 900", colorCode = "#212121"),

                // Blue Gray colors
                MaterialColorModel(colorGroup = "Blue Gray", colorName = "Blue Gray 50", colorCode = "#ECEFF1"),
                MaterialColorModel(colorGroup = "Blue Gray", colorName = "Blue Gray 100", colorCode = "#CFD8DC"),
                MaterialColorModel(colorGroup = "Blue Gray", colorName = "Blue Gray 200", colorCode = "#B0BEC5"),
                MaterialColorModel(colorGroup = "Blue Gray", colorName = "Blue Gray 300", colorCode = "#90A4AE"),
                MaterialColorModel(colorGroup = "Blue Gray", colorName = "Blue Gray 400", colorCode = "#78909C"),
                MaterialColorModel(colorGroup = "Blue Gray", colorName = "Blue Gray 500", colorCode = "#607D8B"),
                MaterialColorModel(colorGroup = "Blue Gray", colorName = "Blue Gray 600", colorCode = "#546E7A"),
                MaterialColorModel(colorGroup = "Blue Gray", colorName = "Blue Gray 700", colorCode = "#455A64"),
                MaterialColorModel(colorGroup = "Blue Gray", colorName = "Blue Gray 800", colorCode = "#37474F"),
                MaterialColorModel(colorGroup = "Blue Gray", colorName = "Blue Gray 900", colorCode = "#263238"),

                // Black and White
                MaterialColorModel(colorGroup = "Black", colorName = "Black", colorCode = "#000000"),
                MaterialColorModel(colorGroup = "White", colorName = "White", colorCode = "#FFFFFF"),
            )

            materialColors[position].isSelected = true

            return@withContext materialColors
        }
    }

}