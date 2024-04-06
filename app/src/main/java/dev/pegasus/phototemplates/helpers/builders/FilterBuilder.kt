package dev.pegasus.phototemplates.helpers.builders

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import dev.pegasus.phototemplates.databinding.FragmentHomeBinding
import dev.pegasus.phototemplates.helpers.extensions.addCleanView
import dev.pegasus.template.databinding.BuilderFilterLayoutBinding
import dev.pegasus.template.helpers.adapters.AdapterFilters
import dev.pegasus.template.helpers.dataClasses.RecycleViewModelClass
import dev.pegasus.template.helpers.dataProviders.DpTemplateTabs
import dev.pegasus.template.utils.ItemDecorationEqualHorizontalMargin
import kotlinx.coroutines.launch

class FilterBuilder(
    private val mBinding: FragmentHomeBinding?,
    private val inflator: LayoutInflater?,
    private val dataProvider: DpTemplateTabs?,
    private val lifecycleScope: LifecycleCoroutineScope,
) {

    private var filtersList: List<RecycleViewModelClass>? = null
    private var selectedFilterPosition = 0
    private var layoutBinding: BuilderFilterLayoutBinding? = null

    /* ---------------------------- Adapter Callbacks --------------------------------------- */
    private val filterItemClicked: (position: Int) -> Unit = { position ->
        filtersList?.let {
            it[selectedFilterPosition].isSelected = false
            adapterFilters.notifyItemChanged(selectedFilterPosition)
            selectedFilterPosition = position
            it[selectedFilterPosition].isSelected = true
            adapterFilters.notifyItemChanged(selectedFilterPosition)
        }
    }

    private val adapterFilters: AdapterFilters by lazy { AdapterFilters(context = mBinding?.root?.context, filterClickedCallback = filterItemClicked )}

    fun inflateFilterLayout() {
        dataProvider?.let {
            lifecycleScope.launch {
                filtersList = it.getFiltersList()
                layoutBinding?.let {
                    mBinding?.flTabLayouts?.addCleanView(it.root)
                } ?: run {
                    layoutBinding = inflator?.let { BuilderFilterLayoutBinding.inflate(it) }
                    layoutBinding?.apply {
                        ifvFilterDone.setOnClickListener { onDoneClicked() }
                        rvFilters.setHasFixedSize(true)
                        rvFilters.addItemDecoration(ItemDecorationEqualHorizontalMargin(spaceSize = 16))
                        rvFilters.adapter = adapterFilters
                        mBinding?.flTabLayouts?.addCleanView(this.root)
                    }
                }
                adapterFilters.submitList(filtersList)

                mBinding?.flTabLayouts?.visibility = View.VISIBLE
            }
        }
    }

    private fun onDoneClicked() {
        mBinding?.flTabLayouts?.visibility = View.INVISIBLE
        mBinding?.flTabLayouts?.removeAllViews()
    }

}