package dev.pegasus.phototemplates

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.pegasus.phototemplates.databinding.FragmentHomeBinding
import dev.pegasus.phototemplates.helpers.builders.TemplateTextBuilder
import dev.pegasus.phototemplates.helpers.viewModels.FrameTemplateViewModel
import dev.pegasus.template.TemplateView
import dev.pegasus.template.dataProviders.DpTemplates
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FragmentHome : Fragment(), TemplateView.OnTemplateViewInitialization {

    private var binding: FragmentHomeBinding? = null
    private val dpTemplates by lazy { DpTemplates() }

    private val templateViewModel: FrameTemplateViewModel by activityViewModel()

    // Collage - Text
    private val templateTextBuilder: TemplateTextBuilder by lazy {
        TemplateTextBuilder(
            binding = binding,
            layoutInflater = layoutInflater,
            fragmentManager = childFragmentManager,
            fragment = this@FragmentHome,
            templateViewModel = templateViewModel
        ) {
            //initItemSelector()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTemplateView()
        templateTextBuilder.inflateStickerModule()
    }

    override fun onResume() {
        super.onResume()
        binding?.tvHome?.setOnTemplateViewListener(this)
    }

    private fun initTemplateView() {
        binding?.tvHome?.setBackgroundFromModel(dpTemplates.list[25])
        binding?.tvHome?.setImageResource(R.drawable.img_pic)
        binding?.tvHome?.setLifecycleOwner(viewLifecycleOwner)
    }

    override fun onInitialized(scaleWidth: Float, scaleHeight: Float) {
        Handler(Looper.getMainLooper()).postDelayed({
            templateTextBuilder.setStickers(dpTemplates.list[25], scaleWidth, scaleHeight)
        }, 1000)
    }

}