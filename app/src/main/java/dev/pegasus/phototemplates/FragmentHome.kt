package dev.pegasus.phototemplates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dev.pegasus.phototemplates.databinding.FragmentHomeBinding
import dev.pegasus.phototemplates.helpers.builders.FilterBuilder
import dev.pegasus.phototemplates.helpers.viewModels.FrameTemplateViewModel
import dev.pegasus.template.helpers.adapters.AdapterTemplateMainTabs
import dev.pegasus.template.helpers.dataClasses.TemplateMainTabsModel
import dev.pegasus.template.helpers.dataProviders.DpTemplateTabs
import dev.pegasus.template.helpers.dataProviders.DpTemplates
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FragmentHome : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private val dpTemplates by lazy { DpTemplates() }
    private val tabsLayoutList = DpTemplateTabs()
    private var selectedTabPosition: Int = 0
    private var rvTabList: List<TemplateMainTabsModel>? = tabsLayoutList.list
    private val mainTabsAdapter by lazy {
        AdapterTemplateMainTabs(
            context = requireContext(),
            itemWidth = resources.displayMetrics.widthPixels / 6,
            itemClickedCallback = itemClicked
        )
    }

    private val templateViewModel: FrameTemplateViewModel by activityViewModel()

    // Collage - Text
    /*private val templateTextBuilder: TemplateTextBuilder by lazy {
        TemplateTextBuilder(
            binding = binding,
            layoutInflater = layoutInflater,
            fragmentManager = childFragmentManager,
            fragment = this@FragmentHome,
            templateViewModel = templateViewModel
        ) {
            //initItemSelector()
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding?.rvMainTabs?.adapter = mainTabsAdapter
        mainTabsAdapter.submitList(rvTabList)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTemplateView()
        /*templateTextBuilder.inflateStickerModule()

        Handler(Looper.getMainLooper()).postDelayed({
            templateTextBuilder.setStickers(dpTemplates.list[27])
        }, 1000)*/

    }

    private fun initTemplateView() {
        binding?.tvTemplate?.setLifecycleOwner(viewLifecycleOwner)
        binding?.tvTemplate?.setBackgroundFromModel(dpTemplates.list[27])
        binding?.tvTemplate?.setImageResource(R.drawable.img_pic)
    }

    /* ---------------- RecyclerView Callbacks --------------------- */
    private val itemClicked: (position: Int) -> Unit = { position ->
        if (selectedTabPosition != position) {
            rvTabList?.let {
                it[selectedTabPosition].isSelected = false
                mainTabsAdapter.notifyItemChanged(selectedTabPosition)
                selectedTabPosition = position
                it[selectedTabPosition].isSelected = true
                mainTabsAdapter.notifyItemChanged(selectedTabPosition)
            }
        }
        initTabSelected(position)
    }

    private fun initTabSelected(position: Int) {
        when(position){
            0 -> {}
            1 -> filterBuilder.inflateFilterLayout()
            2 -> {}
            3 -> {}
            4 -> {}
            5 -> {}
        }
    }

    /* ---------------- Layout Builders --------------------- */
    private val filterBuilder by lazy { FilterBuilder(mBinding = binding, inflator = layoutInflater, dataProvider = tabsLayoutList, lifecycleScope = lifecycleScope) }


}