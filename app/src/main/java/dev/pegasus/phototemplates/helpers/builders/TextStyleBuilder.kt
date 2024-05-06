package dev.pegasus.phototemplates.helpers.builders

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.android.material.chip.ChipGroup
import dev.pegasus.phototemplates.helpers.extensions.addCleanView
import dev.pegasus.template.databinding.BuilderTextLayoutBinding
import dev.pegasus.template.databinding.BuilderTextStyleLayoutBinding
import dev.pegasus.template.databinding.TextStyleAlignLayoutBinding
import dev.pegasus.template.databinding.TextStyleBackgroundLayoutBinding
import dev.pegasus.template.helpers.adapters.AdapterColor
import dev.pegasus.template.helpers.dataClasses.MaterialColorModel
import dev.pegasus.template.helpers.dataProviders.DpTemplateTabs
import dev.pegasus.template.utils.HelperUtils.TAG
import kotlinx.coroutines.launch

class TextStyleBuilder(
    private val mBinding: BuilderTextLayoutBinding?,
    private val inflater: LayoutInflater?,
    private val dataProvider: DpTemplateTabs?,
    private val lifecycleScope: LifecycleCoroutineScope,
) {

    /* ---------------------------- Layout to Inflate -------------------------------------- */
    private var styleLayoutBinding: BuilderTextStyleLayoutBinding? = null
    private var styleBackgroundBinding: TextStyleBackgroundLayoutBinding? = null
    private var styleAlignBinding: TextStyleAlignLayoutBinding? = null

    /* ---------------------------- Lists -------------------------------------- */
    private var backgroundColorList: List<MaterialColorModel>? = null

    /* ---------------------------- Adapter's Item Click Listeners -------------------------------------- */
    private val colorItemClicked: (position: Int) -> Unit = { pos ->
        backgroundColorList?.let {
            when(selectedTabPosition){
                0 -> {
                    it[selectedBackgroundColorPosition].isSelected = false
                    adapterColor.notifyItemChanged(selectedBackgroundColorPosition)
                    selectedBackgroundColorPosition = pos
                    it[selectedBackgroundColorPosition].isSelected = true
                    adapterColor.notifyItemChanged(selectedBackgroundColorPosition)
                }
                1 -> {
                    it[selectedBorderColorPosition].isSelected = false
                    adapterColor.notifyItemChanged(selectedBorderColorPosition)
                    selectedBorderColorPosition = pos
                    it[selectedBorderColorPosition].isSelected = true
                    adapterColor.notifyItemChanged(selectedBorderColorPosition)
                }
            }

        }
    }

    /* ---------------------------- Adapters -------------------------------------- */
    private val adapterColor: AdapterColor by lazy {
        AdapterColor(itemClicked = colorItemClicked)
    }

    private var selectedTabPosition = 0
    private var selectedBackgroundColorPosition = 0
    private var selectedBorderColorPosition = 0

    fun inflateStyleBuilder() {
        styleLayoutBinding?.let {
            mBinding?.flTextControlsLayout?.addCleanView(it.root)
        } ?: run {
            styleLayoutBinding = inflater?.let { BuilderTextStyleLayoutBinding.inflate(it) }
            styleLayoutBinding!!.apply {
                cgCategories.setOnCheckedStateChangeListener(checkStateListener)

                mBinding?.flTextControlsLayout?.addCleanView(this.root)
            }
        }
        mBinding?.flTextControlsLayout?.visibility = View.VISIBLE
    }

    private val checkStateListener = ChipGroup.OnCheckedStateChangeListener { _, checkedIds ->
        Log.d(TAG, "checkStateListener: is called with checkedIds: $checkedIds")
        if (checkedIds.isEmpty()) {
            styleLayoutBinding?.flStyleControlLayout?.visibility = View.GONE
            styleLayoutBinding?.styleMainControlsGroup?.visibility = View.VISIBLE
            selectedTabPosition = -1
            return@OnCheckedStateChangeListener
        }
        checkedIds.forEach { checkedId ->
            if (checkedId != View.NO_ID) {
                styleLayoutBinding?.let { binding ->
                    // invisible the default views
                    binding.styleMainControlsGroup.visibility = View.GONE
                    when (checkedId) {
                        binding.cpBackground.id -> {
                            inflateBackgroundLayout()
                            selectedTabPosition = 0
                        }

                        binding.cpBorder.id -> {
                            inflateBorderLayout()
                            selectedTabPosition = 1
                        }

                        binding.cpAlign.id -> {
                            inflateAlignLayout()
                            selectedTabPosition = 2
                        }
                    }
                }
            }
        }
    }

    private fun inflateBackgroundLayout() {
        dataProvider?.let {
            lifecycleScope.launch {
                if (backgroundColorList.isNullOrEmpty()) backgroundColorList = it.getMaterialColorsList(selectedBackgroundColorPosition)
                else {
                    backgroundColorList!![selectedBorderColorPosition].isSelected = false
                    adapterColor.notifyItemChanged(selectedBorderColorPosition)
                    backgroundColorList!![selectedBackgroundColorPosition].isSelected = true
                    adapterColor.notifyItemChanged(selectedBackgroundColorPosition)
                }
                styleBackgroundBinding?.let {
                    styleLayoutBinding?.flStyleControlLayout?.addCleanView(it.root)
                } ?: run {
                    styleBackgroundBinding = inflater?.let { TextStyleBackgroundLayoutBinding.inflate(it) }
                    styleBackgroundBinding?.let {
                        it.rvMaterialColors.setHasFixedSize(true)
                        it.rvMaterialColors.adapter = adapterColor
                    }
                    styleLayoutBinding?.flStyleControlLayout?.addCleanView(styleBackgroundBinding?.root)
                    adapterColor.submitList(backgroundColorList)
                }

                styleBackgroundBinding?.mtvOpacity?.text = mBinding?.root?.context?.getString(dev.pegasus.template.R.string.opacity)
                styleLayoutBinding?.flStyleControlLayout?.visibility = View.VISIBLE
            }
        }
    }

    private fun inflateBorderLayout() {
        dataProvider?.let {
            lifecycleScope.launch {
                if (backgroundColorList.isNullOrEmpty()) backgroundColorList = it.getMaterialColorsList(selectedBorderColorPosition)
                else {
                    backgroundColorList!![selectedBackgroundColorPosition].isSelected = false
                    adapterColor.notifyItemChanged(selectedBackgroundColorPosition)
                    backgroundColorList!![selectedBorderColorPosition].isSelected = true
                    adapterColor.notifyItemChanged(selectedBorderColorPosition)
                }
                styleBackgroundBinding?.let {
                    styleLayoutBinding?.flStyleControlLayout?.addCleanView(it.root)
                } ?: run {
                    styleBackgroundBinding = inflater?.let { TextStyleBackgroundLayoutBinding.inflate(it) }
                    styleBackgroundBinding?.let {
                        it.rvMaterialColors.setHasFixedSize(true)
                        it.rvMaterialColors.adapter = adapterColor
                    }
                    styleLayoutBinding?.flStyleControlLayout?.addCleanView(styleBackgroundBinding?.root)
                    adapterColor.submitList(backgroundColorList)
                }

                styleBackgroundBinding?.mtvOpacity?.text = mBinding?.root?.context?.getString(dev.pegasus.template.R.string.border)

                styleLayoutBinding?.flStyleControlLayout?.visibility = View.VISIBLE
            }
        }
    }

    private fun inflateAlignLayout() {
        lifecycleScope.launch {
            styleAlignBinding?.let {
                styleLayoutBinding?.flStyleControlLayout?.addCleanView(it.root)
            } ?: run {
                styleAlignBinding = inflater?.let { TextStyleAlignLayoutBinding.inflate(it) }
                styleAlignBinding?.let {
                    it.ifvBoldText.setOnClickListener {  }
                    it.ifvItalicText.setOnClickListener {  }
                    it.ifvUnderlineText.setOnClickListener {  }
                    it.ifvStrokeText.setOnClickListener {  }
                    it.ifvLeftAlign.setOnClickListener {  }
                    it.ifvCenterAlign.setOnClickListener {  }
                    it.ifvRightAlign.setOnClickListener {  }

                    styleLayoutBinding?.flStyleControlLayout?.addCleanView(it.root)
                }
            }

            styleLayoutBinding?.flStyleControlLayout?.visibility = View.VISIBLE
        }
    }

}