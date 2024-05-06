package dev.pegasus.phototemplates.helpers.builders

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LifecycleCoroutineScope
import dev.pegasus.phototemplates.databinding.FragmentHomeBinding
import dev.pegasus.phototemplates.helpers.extensions.addCleanView
import dev.pegasus.template.databinding.BuilderTextLayoutBinding
import dev.pegasus.template.helpers.adapters.AdapterText
import dev.pegasus.template.helpers.dataClasses.RecycleViewModelClass
import dev.pegasus.template.helpers.dataProviders.DpTemplateTabs
import kotlinx.coroutines.launch

class TextBuilder(
    private val mBinding: FragmentHomeBinding?,
    private val inflator: LayoutInflater?,
    private val dataProvider: DpTemplateTabs?,
    private val lifecycleScope: LifecycleCoroutineScope,
) {

    /* ---------------------------- General Required Variables ------------------------------ */
    companion object {
        var selectedTabPosition = 0
    }

    /* ---------------------------- List Required ------------------------------------------ */
    private var textTabList: List<RecycleViewModelClass>? = null

    /* ---------------------------- Layout to Inflate -------------------------------------- */
    private var layoutBinding: BuilderTextLayoutBinding? = null

    /* ---------------------------- Adapter Callbacks --------------------------------------- */
    private val textTabClicked: (position: Int) -> Unit = { position ->
        textTabList?.let {
        if (selectedTabPosition != position) {
                it[selectedTabPosition].isSelected = false
                adapterText.notifyItemChanged(selectedTabPosition)
                selectedTabPosition = position
                it[selectedTabPosition].isSelected = true
                adapterText.notifyItemChanged(selectedTabPosition)
            }

            // Hide the previous tab layout if any
            layoutBinding?.flTextControlsLayout?.visibility = View.GONE

            initTextTabSelected(position)
        }
    }

    /* ---------------------------- Adapter ------------------------------------------------ */
    private val adapterText: AdapterText by lazy {
        AdapterText(
            context = mBinding?.root?.context!!,
            itemWidth = mBinding.root.context?.resources?.displayMetrics?.widthPixels?.div(4) ?: 0,
            itemClicked = textTabClicked
        )
    }

    /* ---------------------------- Functions ----------------------------------------------- */
    fun inflateTextLayout(){
        dataProvider?.let {
            lifecycleScope.launch {
                textTabList = it.getTextTabList(selectedTabPosition)
                layoutBinding?.let {
                    mBinding?.flTabLayouts?.addCleanView(it.root)
                } ?: run {
                    layoutBinding = inflator?.let { BuilderTextLayoutBinding.inflate(it) }
                    layoutBinding!!.apply {
                        ifvTextDone.setOnClickListener { onDoneClicked() }
                        rvTextTabs.setHasFixedSize(true)
                        rvTextTabs.adapter = adapterText
                        mBinding?.flTabLayouts?.addCleanView(this.root)
                    }
                }

                adapterText.submitList(textTabList)

                mBinding?.apply {
                    flTabLayouts.visibility = View.VISIBLE
                    // Show Keyboard at the start and set click listener on editText done button
                    ifvKeyboardDone.setOnClickListener { keyboardDoneBtnClicked() }
                    showKeyboard()
                }
            }
        }
    }

    private fun onDoneClicked() {
        mBinding?.flTabLayouts?.visibility = View.INVISIBLE
        mBinding?.flTabLayouts?.removeAllViews()

        // Clear fonts layout too
        layoutBinding?.flTextControlsLayout?.visibility = View.GONE
        layoutBinding?.flTextControlsLayout?.removeAllViews()
    }

    private fun showKeyboard() {
        mBinding?.viewKeyboard?.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            mBinding?.etTextSticker?.requestFocus()
            val imm: InputMethodManager? = mBinding?.root?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.showSoftInput(mBinding?.etTextSticker, 0)
        }, 300)
    }

    private fun keyboardDoneBtnClicked() {
        val imm: InputMethodManager? = mBinding?.root?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(mBinding?.etTextSticker?.windowToken, 0)

        mBinding?.viewKeyboard?.visibility = View.GONE
    }

    private fun initTextTabSelected(position: Int) {
        when(position){
            0 -> showKeyboard()
            1 -> fontBuilder.inflateFontBuilder()
            2 -> styleBuilder.inflateStyleBuilder()
            3 -> colorBuilder.inflateColorBuilder()
        }
    }

    /* ---------------- Layout Builders --------------------- */
    private val fontBuilder by lazy { TextFontBuilder(mBinding = layoutBinding, inflater = inflator, dataProvider = dataProvider, lifecycleScope = lifecycleScope) }
    private val styleBuilder by lazy { TextStyleBuilder(mBinding = layoutBinding, inflater = inflator, dataProvider = dataProvider, lifecycleScope = lifecycleScope) }
    private val colorBuilder by lazy { TextColorBuilder(mBinding = layoutBinding, inflater = inflator, dataProvider = dataProvider, lifecycleScope = lifecycleScope) }

}