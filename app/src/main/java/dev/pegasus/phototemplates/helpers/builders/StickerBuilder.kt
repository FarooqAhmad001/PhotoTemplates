package dev.pegasus.phototemplates.helpers.builders

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import dev.pegasus.phototemplates.databinding.FragmentHomeBinding
import dev.pegasus.phototemplates.helpers.extensions.addCleanView
import dev.pegasus.template.databinding.BuilderStickerLayoutBinding
import dev.pegasus.template.helpers.dataProviders.DpTemplateTabs
import kotlinx.coroutines.launch

class StickerBuilder(
    private val mBinding: FragmentHomeBinding?,
    private val inflater: LayoutInflater?,
    private val dataProvider: DpTemplateTabs?,
    private val lifecycleScope: LifecycleCoroutineScope,
) {

    /* ---------------------------- Layout to Inflate -------------------------------------- */
    private var layoutBinding: BuilderStickerLayoutBinding? = null

    fun inflateStickerLayout() {
        lifecycleScope.launch {
            layoutBinding?.let {
                mBinding?.flTabLayouts?.addCleanView(it.root)
            } ?: run {
                layoutBinding = inflater?.let { BuilderStickerLayoutBinding.inflate(it) }
                layoutBinding?.ifvStickerDone?.setOnClickListener { onDoneClicked() }
                mBinding?.flTabLayouts?.addCleanView(layoutBinding?.root)
            }

            mBinding?.flTabLayouts?.visibility = View.VISIBLE
        }
    }

    private fun onDoneClicked() {
        mBinding?.flTabLayouts?.visibility = View.INVISIBLE
        mBinding?.flTabLayouts?.removeAllViews()
    }

}