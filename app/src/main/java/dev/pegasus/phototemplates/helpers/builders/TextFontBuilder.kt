package dev.pegasus.phototemplates.helpers.builders

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import dev.pegasus.phototemplates.helpers.extensions.addCleanView
import dev.pegasus.template.databinding.BuilderTextLayoutBinding
import dev.pegasus.template.databinding.ItemRecycleviewFontLayoutBinding
import dev.pegasus.template.helpers.adapters.AdapterFonts
import dev.pegasus.template.helpers.dataClasses.FontModel
import dev.pegasus.template.helpers.dataProviders.DpTemplateTabs
import kotlinx.coroutines.launch

class TextFontBuilder(
    private val mBinding: BuilderTextLayoutBinding?,
    private val inflater: LayoutInflater?,
    private val dataProvider: DpTemplateTabs?,
    private val lifecycleScope: LifecycleCoroutineScope,
) {

    /* ---------------------------- General Required Variables ------------------------------ */
    companion object {
        var selectedFontPosition = 0
    }

    /* ---------------------------- List Required ------------------------------------------ */
    private var fontList: List<FontModel>? = null

    /* ---------------------------- Layout to Inflate -------------------------------------- */
    private var fontLayoutBinding: ItemRecycleviewFontLayoutBinding? = null

    /* ---------------------------- Adapter Callbacks --------------------------------------- */
    private val fontClicked: (position: Int) -> Unit = { position ->
        fontList?.let {
            if (selectedFontPosition != position) {
                it[selectedFontPosition].isSelected = false
                adapterFonts.notifyItemChanged(selectedFontPosition)
                selectedFontPosition = position
                it[selectedFontPosition].isSelected = true
                adapterFonts.notifyItemChanged(selectedFontPosition)
            }
        }
    }

    /* ---------------------------- Adapter ------------------------------------------------ */
    private val adapterFonts: AdapterFonts by lazy {
        AdapterFonts(
            context = mBinding?.root?.context!!,
            itemClicked = fontClicked
        )
    }

    /* ---------------------------- Functions ------------------------------------------------ */
    fun inflateFontBuilder() {
        dataProvider?.let {
            lifecycleScope.launch {
                fontList = it.getFontsList(selectedFontPosition)
                fontLayoutBinding?.let {
                    mBinding?.flTextControlsLayout?.addCleanView(it.root)
                } ?: run {
                    fontLayoutBinding = inflater?.let { ItemRecycleviewFontLayoutBinding.inflate(it) }
                    fontLayoutBinding!!.let {
                        it.rvFonts.setHasFixedSize(true)
                        it.rvFonts.adapter = adapterFonts

                        mBinding?.flTextControlsLayout?.addCleanView(it.root)
                    }
                }
                adapterFonts.submitList(fontList)

                mBinding?.flTextControlsLayout?.visibility = View.VISIBLE
            }
        }
    }

}