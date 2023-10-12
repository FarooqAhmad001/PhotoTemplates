package dev.pegasus.phototemplates

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dev.pegasus.phototemplates.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.templateView.setBackgroundResource(R.drawable.img_bg_one)
        binding.templateView.setImageResource(R.drawable.img_pic)
    }
}