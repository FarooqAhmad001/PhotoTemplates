package dev.pegasus.phototemplates.helpers.dataProvider

import dev.pegasus.phototemplates.R
import dev.pegasus.phototemplates.helpers.model.Font
import dev.pegasus.phototemplates.helpers.model.RecyclerViewItem
import dev.pegasus.template.utils.HelperUtils.isValidPosition

class DpCollageText {

    fun getCollageBackgroundColorList(selectedItem: Int = -1): ArrayList<RecyclerViewItem> {
        val arrayList = ArrayList<RecyclerViewItem>().apply {
            add(RecyclerViewItem(id = 0, imageId = R.drawable.ic_collage_background_small_color_1, featureImageId = R.drawable.ic_collage_background_large_color_1))
            add(RecyclerViewItem(id = 1, imageId = R.drawable.ic_collage_background_small_color_2, featureImageId = R.drawable.ic_collage_background_large_color_2))
            add(RecyclerViewItem(id = 2, imageId = R.drawable.ic_collage_background_small_color_3, featureImageId = R.drawable.ic_collage_background_large_color_3))
            add(RecyclerViewItem(id = 3, imageId = R.drawable.ic_collage_background_small_color_4, featureImageId = R.drawable.ic_collage_background_large_color_4))
            add(RecyclerViewItem(id = 4, imageId = R.drawable.ic_collage_background_small_color_5, featureImageId = R.drawable.ic_collage_background_large_color_5))
            add(RecyclerViewItem(id = 5, imageId = R.drawable.ic_collage_background_small_color_6, featureImageId = R.drawable.ic_collage_background_large_color_6))
            add(RecyclerViewItem(id = 6, imageId = R.drawable.ic_collage_background_small_color_7, featureImageId = R.drawable.ic_collage_background_large_color_7))
            add(RecyclerViewItem(id = 7, imageId = R.drawable.ic_collage_background_small_color_8, featureImageId = R.drawable.ic_collage_background_large_color_8))
            add(RecyclerViewItem(id = 8, imageId = R.drawable.ic_collage_background_small_color_9, featureImageId = R.drawable.ic_collage_background_large_color_9))
            add(RecyclerViewItem(id = 9, imageId = R.drawable.ic_collage_background_small_color_10, featureImageId = R.drawable.ic_collage_background_large_color_10))
            add(RecyclerViewItem(id = 10, imageId = R.drawable.ic_collage_background_small_color_11, featureImageId = R.drawable.ic_collage_background_large_color_11))
            add(RecyclerViewItem(id = 11, imageId = R.drawable.ic_collage_background_small_color_12, featureImageId = R.drawable.ic_collage_background_large_color_12))
            add(RecyclerViewItem(id = 12, imageId = R.drawable.ic_collage_background_small_color_13, featureImageId = R.drawable.ic_collage_background_large_color_13))
            add(RecyclerViewItem(id = 13, imageId = R.drawable.ic_collage_background_small_color_14, featureImageId = R.drawable.ic_collage_background_large_color_14))
            add(RecyclerViewItem(id = 14, imageId = R.drawable.ic_collage_background_small_color_15, featureImageId = R.drawable.ic_collage_background_large_color_15))
            add(RecyclerViewItem(id = 15, imageId = R.drawable.ic_collage_background_small_color_16, featureImageId = R.drawable.ic_collage_background_large_color_16))
            add(RecyclerViewItem(id = 16, imageId = R.drawable.ic_collage_background_small_color_17, featureImageId = R.drawable.ic_collage_background_large_color_17))
            add(RecyclerViewItem(id = 17, imageId = R.drawable.ic_collage_background_small_color_18, featureImageId = R.drawable.ic_collage_background_large_color_18))
            add(RecyclerViewItem(id = 18, imageId = R.drawable.ic_collage_background_small_color_19, featureImageId = R.drawable.ic_collage_background_large_color_19))
            add(RecyclerViewItem(id = 19, imageId = R.drawable.ic_collage_background_small_color_20, featureImageId = R.drawable.ic_collage_background_large_color_20))
            add(RecyclerViewItem(id = 20, imageId = R.drawable.ic_collage_background_small_color_21, featureImageId = R.drawable.ic_collage_background_large_color_21))
            add(RecyclerViewItem(id = 21, imageId = R.drawable.ic_collage_background_small_color_22, featureImageId = R.drawable.ic_collage_background_large_color_22))
            add(RecyclerViewItem(id = 22, imageId = R.drawable.ic_collage_background_small_color_23, featureImageId = R.drawable.ic_collage_background_large_color_23))
            add(RecyclerViewItem(id = 23, imageId = R.drawable.ic_collage_background_small_color_24, featureImageId = R.drawable.ic_collage_background_large_color_24))
            add(RecyclerViewItem(id = 24, imageId = R.drawable.ic_collage_background_small_color_25, featureImageId = R.drawable.ic_collage_background_large_color_25))
        }

        if (selectedItem.isValidPosition(arrayList))
            arrayList[selectedItem].selector = true

        return arrayList
    }

    fun getColorsList(): List<Int> {
        val colorArrayList = ArrayList<Int>().apply {
            add(R.color.collage_text_color_1)
            add(R.color.collage_text_color_2)
            add(R.color.collage_text_color_3)
            add(R.color.collage_text_color_4)
            add(R.color.collage_text_color_5)
            add(R.color.collage_text_color_6)
            add(R.color.collage_text_color_7)
            add(R.color.collage_text_color_8)
            add(R.color.collage_text_color_9)
            add(R.color.collage_text_color_10)
            add(R.color.collage_text_color_11)
            add(R.color.collage_text_color_12)
            add(R.color.collage_text_color_13)
            add(R.color.collage_text_color_14)
            add(R.color.collage_text_color_15)
            add(R.color.collage_text_color_16)
            add(R.color.collage_text_color_17)
            add(R.color.collage_text_color_18)
            add(R.color.collage_text_color_19)
            add(R.color.collage_text_color_20)
            add(R.color.collage_text_color_21)
            add(R.color.collage_text_color_22)
            add(R.color.collage_text_color_23)
            add(R.color.collage_text_color_24)
            add(R.color.collage_text_color_25)
        }
        return colorArrayList
    }

    fun getFontsList(fontType: Int): List<Font> {
        val arrayList = ArrayList<Font>()
        arrayList.add(Font(0, "Normal", "Poppins"))
        arrayList.add(Font(1, "Acme", "Acme"))
        arrayList.add(Font(2, "Aguafina Script", "Aguafina Script"))
        arrayList.add(Font(3, "Alex Brush", "Alex Brush"))
        arrayList.add(Font(4, "Anton", "Anton"))
        arrayList.add(Font(5, "Arizonia", "Arizonia"))
        arrayList.add(Font(6, "Baloo", "Baloo Bhai"))
        arrayList.add(Font(7, "Beth Ellen", "Beth Ellen"))
        arrayList.add(Font(8, "Chicle", "Chicle"))
        arrayList.add(Font(9, "Eagle Lake", "Eagle Lake"))
        arrayList.add(Font(10, "Lobster", "Lobster"))
        arrayList.add(Font(11, "Mouse Memoirs", "Mouse Memoirs"))
        arrayList.add(Font(12, "Permanent Marker", "Permanent Marker"))
        arrayList.add(Font(13, "Redressed", "Redressed"))
        arrayList.add(Font(14, "Roboto", "name=Roboto&amp;weight=500"))
        arrayList.add(Font(15, "Rock Salt", "Rock Salt"))
        arrayList.add(Font(16, "Satisfy", "Satisfy"))

        if (fontType.isValidPosition(arrayList))
            arrayList[fontType].selector = true
        return arrayList
    }
}