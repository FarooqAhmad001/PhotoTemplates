package dev.pegasus.phototemplates.helpers.builders

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import dev.pegasus.phototemplates.helpers.extensions.addCleanView
import dev.pegasus.template.databinding.BuilderTextLayoutBinding
import dev.pegasus.template.databinding.TextStyleBackgroundLayoutBinding
import dev.pegasus.template.helpers.adapters.AdapterColor
import dev.pegasus.template.helpers.dataClasses.MaterialColorModel
import dev.pegasus.template.helpers.dataProviders.DpTemplateTabs
import kotlinx.coroutines.launch

class TextColorBuilder(
    private val mBinding: BuilderTextLayoutBinding?,
    private val inflater: LayoutInflater?,
    private val dataProvider: DpTemplateTabs?,
    private val lifecycleScope: LifecycleCoroutineScope,
) {


    private var layoutBinding: TextStyleBackgroundLayoutBinding? = null
    private var colorList: List<MaterialColorModel>? = null
    private var selectedColorPosition = 0

    private val colorItemClicked: (position: Int) -> Unit = { pos ->
        colorList?.let {
            it[selectedColorPosition].isSelected = false
            adapterColor.notifyItemChanged(selectedColorPosition)
            selectedColorPosition = pos
            it[selectedColorPosition].isSelected = true
            adapterColor.notifyItemChanged(selectedColorPosition)
        }
    }

    private val adapterColor: AdapterColor by lazy {
        AdapterColor(itemClicked = colorItemClicked)
    }

    fun inflateColorBuilder() {
        dataProvider?.let {
            lifecycleScope.launch {
                colorList = it.getMaterialColorsList(selectedColorPosition)
                layoutBinding?.let {
                    mBinding?.flTextControlsLayout?.addCleanView(it.root)
                } ?: run {
                    layoutBinding = inflater?.let { TextStyleBackgroundLayoutBinding.inflate(it) }
                    layoutBinding?.let {
                        it.rvMaterialColors.setHasFixedSize(true)
                        it.rvMaterialColors.adapter = adapterColor
                    }
                    mBinding?.flTextControlsLayout?.addCleanView(layoutBinding?.root)
                }

                adapterColor.submitList(colorList)
                mBinding?.flTextControlsLayout?.visibility = View.VISIBLE
            }
        }
    }

}