package dev.pegasus.phototemplates.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.pegasus.phototemplates.R
import dev.pegasus.phototemplates.databinding.FragmentEmojiBinding

class FragmentEmoji : Fragment() {

    private var mBinding: FragmentEmojiBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentEmojiBinding.inflate(inflater, container, false)

        return mBinding?.root
    }

}