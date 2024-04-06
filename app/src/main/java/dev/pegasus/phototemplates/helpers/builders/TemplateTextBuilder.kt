//package dev.pegasus.phototemplates.helpers.builders
//
//import android.content.res.Resources
//import android.graphics.Color
//import android.text.Editable
//import android.text.Layout
//import android.text.TextWatcher
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import androidx.core.content.ContextCompat
//import androidx.core.view.isVisible
//import androidx.fragment.app.FragmentManager
//import androidx.lifecycle.LifecycleOwner
//import androidx.lifecycle.lifecycleScope
//import com.google.android.material.snackbar.Snackbar
//import dev.pegasus.phototemplates.FragmentHome
//import dev.pegasus.phototemplates.R
//import dev.pegasus.phototemplates.databinding.FragmentHomeBinding
//import dev.pegasus.phototemplates.databinding.ViewTemplateTextBinding
//import dev.pegasus.phototemplates.helpers.adapters.AdapterCollageFonts
//import dev.pegasus.phototemplates.helpers.adapters.AdapterRecyclerView
//import dev.pegasus.phototemplates.helpers.dataProvider.DpCollageText
//import dev.pegasus.phototemplates.helpers.extensions.addCleanView
//import dev.pegasus.phototemplates.helpers.interfaces.OnFontItemClickListener
//import dev.pegasus.phototemplates.helpers.interfaces.OnRecyclerViewItemClickListener
//import dev.pegasus.phototemplates.helpers.managers.TextManager
//import dev.pegasus.phototemplates.helpers.model.Font
//import dev.pegasus.phototemplates.helpers.model.RecyclerViewItem
//import dev.pegasus.phototemplates.helpers.viewModels.FrameTemplateViewModel
//import dev.pegasus.regret.RegretManager
//import dev.pegasus.regret.enums.CaseType
//import dev.pegasus.regret.interfaces.RegretListener
//import dev.pegasus.stickers.StickerView
//import dev.pegasus.stickers.TextSticker
//import dev.pegasus.stickers.helper.Sticker
//import dev.pegasus.stickers.ui.DrawableSticker
//import dev.pegasus.template.TemplateEditText
//import dev.pegasus.template.dataClasses.StickerItem
//import dev.pegasus.template.dataClasses.StickerType
//import dev.pegasus.template.dataClasses.TemplateModel
//import dev.pegasus.template.utils.HelperUtils
//import dev.pegasus.template.utils.HelperUtils.isValidPosition
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlin.coroutines.resume
//import kotlin.coroutines.resumeWithException
//import kotlin.coroutines.suspendCoroutine
//
//class TemplateTextBuilder(
//    private val binding: FragmentHomeBinding?,
//    private val layoutInflater: LayoutInflater,
//    private val fragmentManager: FragmentManager,
//    private val fragment: FragmentHome,
//    private val templateViewModel: FrameTemplateViewModel,
//    private val itemSelectorCallback: () -> Unit,
//): TemplateEditText.OnKeyboardSystemBackButtonClick {
//    private val globalContext by lazy { binding!!.root.context }
//
//    private var layoutBinding: ViewTemplateTextBinding? = null
//    private var fontColor: RecyclerViewItem? = null
//    private var fontStyle: Font? = null
//
//    private val adapterRvColor: AdapterRecyclerView by lazy { AdapterRecyclerView(itemClickRvColor, caseType = 1) }
//    private val adapterRvFonts: AdapterCollageFonts by lazy { AdapterCollageFonts(itemClickRvFonts) }
//
//    private val textManager by lazy { TextManager(globalContext) }
//    private val dpCollageText by lazy { DpCollageText() }
//
//    private val regretManagerList = ArrayList<RegretManager>()
//    private var regretPosition = 0
//
//    private val addTextString by lazy { fragment.resources.getString(R.string.add_text) }
//
//    /**
//     * @property textAtTheStart: It is used to add the old text again if cross button is pressed
//     */
//    private var textAtTheStart: String = ""
//
//    private var isStickerUpdating: Boolean = false
//    private var isKeyboardOpened: Boolean = false
//    private var newlyAddedTextSticker: TextSticker? = null
//    private var selectedStickerId: Int? = null
//
//    fun inflateStickerModule(){
//        initStickerView()
//
//        binding?.etTypeTemplate?.addTextChangedListener(textWatcher)
//        binding?.mbAddText?.setOnClickListener { onAddTextClick() }
//        binding?.ifvCloseTemplate?.setOnClickListener { onCancelClick() }
//        binding?.ifvDoneTemplate?.setOnClickListener { onDoneClick() }
//        binding?.etTypeTemplate?.setOnBackButtonPressedListener(this@TemplateTextBuilder)
//    }
//
//    private fun initStickerView() {
//        binding?.stvHome?.isConstrained = true
//        binding?.stvHome?.setLifecycleOwner(fragment.viewLifecycleOwner)
//        binding?.stvHome?.onStickerOperationListener = stickerListener
//    }
//
//    private val stickerListener = object : StickerView.OnStickerOperationListener {
//
//        override fun onStickerAdded(sticker: Sticker) {
//            if (sticker is TextSticker) {
//                addItemRegretManager(sticker)
//                selectedStickerId = sticker.stickerId
//            }
//        }
//
//        override fun onStickerClicked(sticker: Sticker) {
//            Log.d(HelperUtils.TAG, "onStickerClicked: is called")
//            val currentSticker = binding?.stvHome?.currentSticker
//            if (currentSticker is TextSticker) {
//                if (currentSticker.stickerId == selectedStickerId) return
//                selectedStickerId = currentSticker.stickerId
//                resetTemplateEditText(currentSticker)
//            }
//        }
//
//        override fun onStickerDeleted(sticker: Sticker) {
//            updateUI(false)
//            binding?.etTypeTemplate?.hideKeyboard()
//            isKeyboardOpened = false
//
//            fragment.lifecycleScope.launch(Dispatchers.Default) {
//                if (sticker is TextSticker) {
//                    regretManagerList.removeAt(getDeletedStickerPosition(sticker))
//                }
//            }
//
//            /*if (regretManagerList.isNotEmpty() && regretManagerList.size > regretPosition) {
//                regretManagerList.removeAt(regretPosition)
//            }*/
//        }
//
//        override fun onStickerTouchedDown(sticker: Sticker) {
//            Log.d(HelperUtils.TAG, "onStickerTouchedDown: is called")
//        }
//
//        override fun onStickerDuplicate(sticker: Sticker, callback: (String) -> Unit) {
//            if (sticker is TextSticker) {
//                fragment.lifecycleScope.launch {
//                    val txt = getPreviousText(sticker)
//                    callback.invoke(txt)
//                }
//            }
//        }
//
//        override fun onStickerZoomFinished(sticker: Sticker) {}
//        override fun onStickerDragFinished(sticker: Sticker) {}
//        override fun onStickerDoubleTapped(sticker: Sticker) {}
//        override fun onStickerUpdate(sticker: Sticker) {
//            Log.d(HelperUtils.TAG, "onStickerUpdate: is called")
//            isStickerUpdating = true
//            updateUI(true)
//            showKeyboard()
//            if (sticker is TextSticker) resetTemplateEditText(sticker)
//        }
//    }
//
//    private fun onAddTextClick() {
//        updateUI(true)
//        showKeyboard()
//        addSticker()
//    }
//
//    private fun showKeyboard() {
//        isKeyboardOpened = true
//        binding?.etTypeTemplate?.showKeyboard()
//        binding?.etTypeTemplate?.requestFocus()
//    }
//
//    private fun addSticker(newText: String = addTextString) {
//        binding?.apply {
//            etTypeTemplate.setText("")
//
//            fragment.lifecycleScope.launch {
//                newlyAddedTextSticker = TextSticker(globalContext).apply {
//                    text = newText
//                    setTextColor(Color.WHITE)
//                    setTextAlign(Layout.Alignment.ALIGN_CENTER)
//                    resizeText()
//                }
//                // Here, we don't update the sticker bcz it is a newly added sticker
//                isStickerUpdating = false
//
//                when (stvHome.stickerCount < 20) {
//                    true -> stvHome.addSticker(newlyAddedTextSticker!!)
//                    false -> root.let { Snackbar.make(it, fragment.resources.getString(R.string.limit_reached), Snackbar.LENGTH_LONG).show() }
//                }
//            }
//        }
//    }
//
//    private fun onCancelClick() {
//        updateUI(false)
//        binding?.etTypeTemplate?.hideKeyboard()
//        isKeyboardOpened = false
//
//        newlyAddedTextSticker?.let {
//            binding?.stvHome?.remove(it)
//        }
//        newlyAddedTextSticker = null
//
//        val currentSticker = binding?.stvHome?.currentSticker
//        if (currentSticker is TextSticker) {
//            if (isStickerUpdating) {
//                currentSticker.text = regretManagerList[regretPosition].getPreviousText()
//                currentSticker.resizeText()
//                binding?.stvHome?.invalidate()
//            } else {
//                currentSticker.text = textAtTheStart
//                currentSticker.resizeText()
//                binding?.stvHome?.invalidate()
//            }
//        }
//
//        /*val currentSticker = binding?.stvHome?.currentSticker
//        if (currentSticker is TextSticker) {
//            if (isStickerUpdating) {
//                currentSticker.text = regretManagerList[regretPosition].getPreviousText()
//                currentSticker.resizeText()
//                binding?.stvHome?.invalidate()
//            } else {
//                if (currentSticker.stickerId == newlyAddedTextSticker?.stickerId) {
//                    binding?.stvHome?.removeCurrentSticker()
//                } else {
//                    currentSticker.text = regretManagerList[regretPosition].getPreviousText()
//                    currentSticker.resizeText()
//                    binding?.stvHome?.invalidate()
//                }
//            }
//        }*/
//    }
//
//    private fun onDoneClick() {
//        updateUI(false)
//        binding?.etTypeTemplate?.hideKeyboard()
//        isKeyboardOpened = false
//
//        newlyAddedTextSticker?.let { sticker ->
//            val text = sticker.text
//            if ((text.isNullOrEmpty() || text.isBlank() || text == addTextString) && regretPosition.isValidPosition(regretManagerList)) {
//                textManager.applyNewText("", binding?.stvHome!!, regretManagerList[regretPosition])
//                regretManagerList.removeAt(regretPosition)
//                binding.stvHome.remove(sticker)
//            } else {
//                fragment.lifecycleScope.launch {
//                    sticker.text?.let { textManager.applyNewText(it, binding?.stvHome!!, regretManagerList[regretPosition]) }
//                }
//            }
//            newlyAddedTextSticker = null
//        } ?: run {
//            if (isStickerUpdating) {
//                fragment.lifecycleScope.launch {
//                    binding?.stvHome!!.currentSticker?.let { sticker ->
//                        if (sticker is TextSticker) sticker.text?.let { textManager.applyNewText(it, binding.stvHome, regretManagerList[regretPosition]) }
//                        // To show the customization layout when the sticker is successfully added
//                        showViews()
//                    }
//                }
//            }
//        }
//
//        /*val currentSticker = binding?.stvHome?.currentSticker
//        if (binding?.stvHome?.currentSticker is TextSticker) {
//            val text = (currentSticker as TextSticker).text
//            if ((text.isNullOrEmpty() || text.isBlank() || text == addTextString) && regretPosition.isValidPosition(regretManagerList)) {
//                textManager.applyNewText("", binding.stvHome, regretManagerList[regretPosition])
//                regretManagerList.removeAt(regretPosition)
//                binding.stvHome.removeCurrentSticker()
//            }
//            fragment.lifecycleScope.launch {
//                currentSticker.text?.let { textManager.applyNewText(it, binding.stvHome, regretManagerList[regretPosition]) }
//                showViews()
//            }
//        }*/
//    }
//
//    private fun updateUI(showingKeyboard: Boolean) {
//        binding?.mbAddText?.isVisible = !showingKeyboard
//        binding?.ifvDoneTemplate?.isVisible = showingKeyboard
//        binding?.ifvCloseTemplate?.isVisible = showingKeyboard
//        binding?.backgroundView?.isVisible = showingKeyboard
//    }
//
//    private fun resetTemplateEditText(sticker: TextSticker) {
//        fragment.lifecycleScope.launch {
//            textAtTheStart = getPreviousText(sticker)
//            if (textAtTheStart == addTextString) binding?.etTypeTemplate?.setText("")
//            else {
//                binding?.etTypeTemplate?.setText(textAtTheStart)
//                binding?.etTypeTemplate?.setSelection(textAtTheStart.length)
//            }
//        }
//    }
//
//    private val textWatcher = object : TextWatcher {
//        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//        override fun afterTextChanged(text: Editable?) {
//            Log.d(HelperUtils.TAG, "afterTextChanged: $text")
//            binding?.stvHome?.currentSticker?.let {
//                if (it is TextSticker) {
//                    it.text = text.toString()
//                    // If user delete all characters than we will show our hint
//                    if (it.text.isNullOrEmpty()) it.text = addTextString
//                    it.resizeText()
//                    binding.stvHome.invalidate()
//                }
//            }
//        }
//    }
//
//    private fun addItemRegretManager(sticker: TextSticker) {
//        //if (!isAdded) return
//        val regretManager = RegretManager(globalContext, object : RegretListener {
//            override fun onDo(key: CaseType, value: Any?, regretType: Int) {}
//            override fun onCanDo(canUndo: Boolean, canRedo: Boolean) {}
//        })
//        regretManagerList.add(regretManager)
//        regretPosition = regretManagerList.size - 1
//        if (regretPosition.isValidPosition(regretManagerList)) {
//            regretManagerList[regretPosition].setView(sticker)
//            textManager.applyDefaultText(regretManagerList[regretPosition], sticker.text!!)
//            //textManager.applyDefaultTypeFace(regretManagerList[regretPosition], dpCollageText.getFontsList(0)[0].fontType)
//            //textManager.applyDefaultColor(regretManagerList[regretPosition])
//        }
//    }
//
//    private suspend fun getPreviousText(sticker: TextSticker): String = suspendCoroutine { continuation ->
//        fragment.lifecycleScope.launch {
//            regretManagerList.forEachIndexed { index, regretManager ->
//                if (regretManager.getView() == sticker) {
//                    regretPosition = index
//                    continuation.resume(regretManager.getPreviousText())
//                    return@launch
//                }
//            }
//            // If the sticker is not found, we show the error message
//            //continuation.resumeWithException(Resources.NotFoundException(" getPreviousText: Sticker not found"))
//        }
//    }
//
//    private suspend fun getDeletedStickerPosition(sticker: TextSticker): Int = suspendCoroutine { continuation ->
//        fragment.lifecycleScope.launch {
//            regretManagerList.forEachIndexed { index, regretManager ->
//                if (regretManager.getView() == sticker) {
//                    continuation.resume(index)
//                    return@launch
//                }
//            }
//            // If the sticker is not found, we show the error message
//            //continuation.resumeWithException(Resources.NotFoundException(" getDeletedStickerPosition: Sticker not found"))
//        }
//    }
//
//    /*fun inflateText() {
//        initStickerView()
//        isNewSticker = binding.stvHome.stickerCount == 0
//        if (isNewSticker) {
//           // showKeyboard()
//           // showTextBoxDialog(null)
//            return
//        } else {
//            showViews()
//        }
//    }*/
//
//    /*private fun initStickerView() {
//        val deleteIcon = BitmapStickerIcon(ContextCompat.getDrawable(globalContext, R.drawable.ic_cross), BitmapStickerIcon.LEFT_TOP)
//        deleteIcon.iconEvent = DeleteIconEvent()
//
//        binding.svContainerCollage.icons = arrayListOf(deleteIcon)
//        binding.svContainerCollage.isLocked = false
//        binding.svContainerCollage.isConstrained = true
//
//        binding.svContainerCollage.onStickerOperationListener = object : StickerView.OnStickerOperationListener {
//            override fun onStickerAdded(sticker: Sticker) {
//                showLog(globalContext, "listener", "onStickerAdded", "called")
//                binding.root.setOnClickListener { removeBorder() }
//
//                val fontColor = dpCollageText.getColorsList()[collageViewModel.fontColor]
//                val fontStyle = dpCollageText.getFontsList(0)[collageViewModel.fontStyle]
//
//                textManager.applyTextColor(fontColor, binding.svContainerCollage)
//                textManager.applyDefaultTypeFace(binding.svContainerCollage, fontStyle.fontType)
//
//                collageViewModel.saveCustomizedText(sticker)
//            }
//
//            override fun onStickerClicked(sticker: Sticker) {
//                binding.svContainerCollage.showBorder(true)
//            }
//
//            override fun onStickerDeleted(sticker: Sticker) {
//                showLog(globalContext, "listener", "onStickerDeleted", "called")
//                onCancelClick()
//            }
//
//            override fun onStickerDragFinished(sticker: Sticker) {
//                showLog(globalContext, "listener", "onStickerDragFinished", "called")
//            }
//
//            override fun onStickerTouchedDown(sticker: Sticker, isUpdate: Boolean) {
//                showLog(globalContext, "listener", "onStickerTouchedDown", "called")
//            }
//
//            override fun onStickerZoomFinished(sticker: Sticker) {
//                showLog(globalContext, "listener", "onStickerZoomFinished", "called")
//            }
//
//            override fun onStickerDuplicate(sticker: Sticker, callback: (String) -> Unit) {
//                showLog(globalContext, "listener", "onStickerFlipped", "called")
//            }
//
//            override fun onStickerDoubleTapped(sticker: Sticker) {
//                showLog(globalContext, "listener", "onStickerDoubleTapped", "called")
//                showKeyboard()
//                showTextBoxDialog((sticker as TextSticker).text)
//            }
//
//            override fun onStickerUpdated(sticker: Sticker) {
//                TODO("Not yet implemented")
//            }
//        }
//    }*/
//
//    /*private fun showKeyboard() {
//        val imm: InputMethodManager? = globalContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
//        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
//    }*/
//
//    /*private fun showTextBoxDialog(text: String?) {
//        dialogTextBox?.dismiss()
//        dialogTextBox = DialogTextBox.newInstance(text)
//        dialogTextBox?.let {
//            it.setListener(object : OnTextDoneClickListener {
//                override fun onDoneText(newText: String, isUpdate: Boolean) {
//                    if (isUpdate) updateSticker(newText)
//                    else addSticker(newText, true)
//                }
//                override fun onCancelText() {
//                    onCancelClick()
//                }
//            })
//            it.show(fragmentManager, "dialog_text_box")
//        }
//    }*/
//
//    /*private fun updateSticker(newText: String) {
//        binding.svContainerCollage.currentSticker?.let {
//            (it as TextSticker).text = newText
//            textManager.applyNewText(newText, binding.svContainerCollage)
//            it.resizeText()
//            binding.svContainerCollage.invalidate()
//        }
//    }*/
//
//    /*private fun addSticker(newText: String, showViews: Boolean = true) {
//        val sticker = TextSticker(globalContext)
//        sticker.text = newText
//        sticker.setTextColor(Color.RED)
//        sticker.setTextAlign(Layout.Alignment.ALIGN_CENTER)
//        binding.svContainerCollage.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                sticker.resizeText()
//                binding.svContainerCollage.viewTreeObserver.removeOnGlobalLayoutListener(this)
//                if (binding.svContainerCollage.stickerCount < 20) {
//                    binding.svContainerCollage.addSticker(sticker)
//                    if (showViews) {
//                        showViews()
//                    }
//                } else {
//                    Snackbar.make(binding.root, R.string.limit_reached, Snackbar.LENGTH_SHORT).show()
//                }
//            }
//        })
//    }*/
//
//    /*fun addSavedSticker() {
//        initStickerView()
//        (collageViewModel.getSticker() as? TextSticker)?.text?.let {
//            addSticker(it, false)
//        }
//    }*/
//
//    /* ----------------------------------------- UI ----------------------------------------- */
//
//    private fun showViews(){
//        binding?.stvHome?.showBorder(true)
//        binding?.flToolsTemplate?.visibility = View.VISIBLE
//        layoutBinding?.let {
//            binding?.flToolsTemplate?.addCleanView(it.root)
//        } ?: run {
//            layoutBinding = ViewTemplateTextBinding.inflate(layoutInflater).also {
//                it.ifvCloseViewCollageText.setOnClickListener {
//                    onCancelClick()
//                    onTextBuilderCancelClick()
//                }
//                it.ifvDoneViewCollageText.setOnClickListener { onTextBuilderDoneClick() }
//
//                it.incColorsCollageText.root.itemAnimator = null
//                it.incColorsCollageText.root.adapter = adapterRvColor
//
//                it.incFontsCollageText.root.itemAnimator = null
//                it.incFontsCollageText.root.adapter = adapterRvFonts
//
//                binding?.flToolsTemplate?.addCleanView(it.root)
//            }
//        }
//        adapterRvColor.submitList(dpCollageText.getCollageBackgroundColorList(templateViewModel.fontColor))
//        adapterRvFonts.submitList(dpCollageText.getFontsList(templateViewModel.fontStyle))
//    }
//
//    private fun onTextBuilderCancelClick() {
//        removeBorder(false)
//        binding?.flToolsTemplate?.removeAllViews()
//        binding?.flToolsTemplate?.visibility = View.GONE
//
//        /*if (isNewSticker) {
//            binding.svContainerCollage.removeAllStickers()
//            collageViewModel.clearCustomizedText()
//            collageViewModel.fontColor = 0
//            collageViewModel.fontStyle = 0
//        }*/
//        itemSelectorCallback.invoke()
//    }
//
//    private fun onTextBuilderDoneClick() {
//        removeBorder(true)
//        binding?.flToolsTemplate?.removeAllViews()
//        binding?.flToolsTemplate?.visibility = View.GONE
//        //templateViewModel.saveCustomizedText(binding.svContainerCollage.currentSticker)
//        itemSelectorCallback.invoke()
//    }
//
//    private fun removeBorder(showBorder: Boolean = true) {
//        binding?.stvHome?.showBorder(showBorder)
//    }
//
//    private val itemClickRvColor: OnRecyclerViewItemClickListener = object : OnRecyclerViewItemClickListener {
//        override fun onItemClick(recyclerViewItem: RecyclerViewItem) {
//            templateViewModel.fontColor = recyclerViewItem.id
//            binding?.stvHome?.showBorder(true)
//            binding?.stvHome?.let { textManager.applyTextColor(dpCollageText.getColorsList()[recyclerViewItem.id], it) }
//            val newList = dpCollageText.getCollageBackgroundColorList(recyclerViewItem.id)
//            adapterRvColor.submitList(newList)
//        }
//    }
//
//    private val itemClickRvFonts: OnFontItemClickListener = object : OnFontItemClickListener {
//        override fun onFontItem(font: Font) {
//            templateViewModel.fontStyle = font.id
//            binding?.stvHome?.showBorder(true)
//            binding?.stvHome?.let { textManager.applyDefaultTypeFace(it, font.fontType) }
//            val newList = dpCollageText.getFontsList(font.id)
//            adapterRvFonts.submitList(newList)
//        }
//    }
//
//    fun setStickers(model: TemplateModel? = null) {
//        model?.let { template ->
//            if (template.stickers?.isNotEmpty() == true){
//                template.stickers?.forEachIndexed { _, sticker ->
//                    binding?.stvHome?.addPredefinedSticker(sticker, template.width, template.height)
//                }
//                binding?.stvHome?.invalidate()
//            }
//        }
//    }
//
//    override fun onBackButtonPressed() {
//        onCancelClick()
//    }
//
//}