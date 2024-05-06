package dev.pegasus.phototemplates.helpers.builders

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import dev.pegasus.phototemplates.databinding.FragmentHomeBinding
import dev.pegasus.phototemplates.helpers.extensions.addCleanView
import dev.pegasus.template.databinding.BuilderAdjustLayoutBinding
import dev.pegasus.template.helpers.adapters.AdapterAdjust
import dev.pegasus.template.helpers.dataClasses.RecycleViewModelClass
import dev.pegasus.template.helpers.dataProviders.DpTemplateTabs
import dev.pegasus.template.utils.ItemDecorationEqualHorizontalMargin
import kotlinx.coroutines.launch

class AdjustBuilder(
    private val mBinding: FragmentHomeBinding?,
    private val inflator: LayoutInflater?,
    private val dataProvider: DpTemplateTabs?,
    private val lifecycleScope: LifecycleCoroutineScope,
) {

    private var adjustControlsList: List<RecycleViewModelClass>? = null
    private var selectedAdjustControlPosition = 0
    private var layoutBinding: BuilderAdjustLayoutBinding? = null

    /* ---------------------------- Adapter Callbacks --------------------------------------- */
    private val adjustItemClicked: (position: Int) -> Unit = { position ->
        adjustControlsList?.let {
            it[selectedAdjustControlPosition].isSelected = false
            adapterAdjust.notifyItemChanged(selectedAdjustControlPosition)
            selectedAdjustControlPosition = position
            it[selectedAdjustControlPosition].isSelected = true
            adapterAdjust.notifyItemChanged(selectedAdjustControlPosition)
        }
    }

    private val adapterAdjust: AdapterAdjust by lazy {
        AdapterAdjust(context = mBinding?.root?.context!!, itemClicked = adjustItemClicked)
    }

    fun inflateAdjustLayout() {
        dataProvider?.let {
            lifecycleScope.launch {
                adjustControlsList = it.getAdjustControlsList()
                layoutBinding?.let {
                    mBinding?.flTabLayouts?.addCleanView(it.root)
                } ?: run {
                    layoutBinding = inflator?.let { BuilderAdjustLayoutBinding.inflate(it) }
                    layoutBinding?.apply {
                        ifvAdjustDone.setOnClickListener { onDoneClicked() }
                        rvAdjust.setHasFixedSize(true)
                        rvAdjust.addItemDecoration(ItemDecorationEqualHorizontalMargin(20))
                        rvAdjust.adapter = adapterAdjust
                        mBinding?.flTabLayouts?.addCleanView(this.root)
                    }
                }
                adapterAdjust.submitList(adjustControlsList)

                mBinding?.flTabLayouts?.visibility = View.VISIBLE
            }
        }
    }

    private fun onDoneClicked() {
        mBinding?.flTabLayouts?.visibility = View.INVISIBLE
        mBinding?.flTabLayouts?.removeAllViews()
    }

}