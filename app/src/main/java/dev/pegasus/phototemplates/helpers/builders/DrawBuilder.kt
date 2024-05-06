package dev.pegasus.phototemplates.helpers.builders

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleCoroutineScope
import dev.pegasus.phototemplates.R
import dev.pegasus.phototemplates.databinding.FragmentHomeBinding
import dev.pegasus.phototemplates.helpers.extensions.addCleanView
import dev.pegasus.template.databinding.BuilderDrawLayoutBinding
import dev.pegasus.template.helpers.adapters.AdapterColor
import dev.pegasus.template.helpers.dataClasses.MaterialColorModel
import dev.pegasus.template.helpers.dataProviders.DpTemplateTabs
import kotlinx.coroutines.launch

class DrawBuilder(
    private val mBinding: FragmentHomeBinding?,
    private val inflater: LayoutInflater?,
    private val dataProvider: DpTemplateTabs?,
    private val lifecycleScope: LifecycleCoroutineScope,
) {

    private var selectedColorPosition = 0
    private val unselectedBtnColor = ContextCompat.getColor(mBinding?.root?.context!!, dev.pegasus.template.R.color.unselected_tab_color)

    /* ---------------------------- Layout to Inflate -------------------------------------- */
    private var layoutBinding: BuilderDrawLayoutBinding? = null

    /* ---------------------------- Lists -------------------------------------- */
    private var colorList: List<MaterialColorModel>? = null

    /* ---------------------------- Adapter's Item Click Listeners -------------------------------------- */
    private val colorItemClicked: (position: Int) -> Unit = { pos ->
        colorList?.let {
            it[selectedColorPosition].isSelected = false
            adapterColor.notifyItemChanged(selectedColorPosition)
            selectedColorPosition = pos
            it[selectedColorPosition].isSelected = true
            adapterColor.notifyItemChanged(selectedColorPosition)
        }

    }

    /* ---------------------------- Adapters -------------------------------------- */
    private val adapterColor: AdapterColor by lazy { AdapterColor(itemClicked = colorItemClicked) }

    fun inflateDrawLayout() {
        dataProvider?.let {
            lifecycleScope.launch {
                colorList = it.getMaterialColorsList(selectedColorPosition)
                layoutBinding?.let {
                    mBinding?.flTabLayouts?.addCleanView(it.root)
                } ?: run {
                    layoutBinding = inflater?.let { BuilderDrawLayoutBinding.inflate(it) }
                    layoutBinding!!.apply {
                        mbBrush.setOnClickListener { tabButtonClicked(0) }
                        mbErase.setOnClickListener { tabButtonClicked(1) }
                        ifvDrawDone.setOnClickListener { onDoneClicked() }
                        rvBrushColor.setHasFixedSize(true)
                        rvBrushColor.adapter = adapterColor
                    }
                    mBinding?.flTabLayouts?.addCleanView(layoutBinding?.root)
                }

                adapterColor.submitList(colorList)

                mBinding?.flTabLayouts?.visibility = View.VISIBLE
            }
        }
    }

    private fun tabButtonClicked(position: Int) {
        layoutBinding?.apply {
            mbBrush.setTextColor(unselectedBtnColor)
            mbBrush.icon.setTint(unselectedBtnColor)
            mbErase.setTextColor(unselectedBtnColor)
            mbErase.icon.setTint(unselectedBtnColor)
            when(position){
                0 -> {
                    mbBrush.setTextColor(Color.WHITE)
                    mbBrush.icon.setTint(Color.WHITE)
                    rvBrushColor.visibility = View.VISIBLE
                }
                1 -> {
                    mbErase.setTextColor(Color.WHITE)
                    mbErase.icon.setTint(Color.WHITE)
                    rvBrushColor.visibility = View.GONE
                }
            }
        }
    }

    private fun onDoneClicked() {
        mBinding?.flTabLayouts?.visibility = View.INVISIBLE
        mBinding?.flTabLayouts?.removeAllViews()
    }

}