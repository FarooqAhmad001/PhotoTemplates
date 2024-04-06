package dev.pegasus.phototemplates.helpers.builders

import android.view.LayoutInflater
import androidx.lifecycle.LifecycleCoroutineScope
import dev.pegasus.phototemplates.databinding.FragmentHomeBinding
import dev.pegasus.template.helpers.dataProviders.DpTemplateTabs

class AdjustBuilder(
    private val mBinding: FragmentHomeBinding?,
    private val inflator: LayoutInflater?,
    private val dataProvider: DpTemplateTabs?,
    private val lifecycleScope: LifecycleCoroutineScope,
) {

}