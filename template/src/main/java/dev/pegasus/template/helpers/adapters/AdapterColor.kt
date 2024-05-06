package dev.pegasus.template.helpers.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.pegasus.template.databinding.ItemTextStyleBackgroundBinding
import dev.pegasus.template.helpers.dataClasses.MaterialColorModel

class AdapterColor(
    val itemClicked: (position: Int) -> Unit
): ListAdapter<MaterialColorModel, AdapterColor.CustomViewHolder>(diffUtil) {

    inner class CustomViewHolder(val binding: ItemTextStyleBackgroundBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.viewColor.setOnClickListener {
                itemClicked.invoke(absoluteAdapterPosition)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MaterialColorModel>() {
            override fun areItemsTheSame(oldItem: MaterialColorModel, newItem: MaterialColorModel): Boolean {
                return oldItem.isSelected == newItem.isSelected
            }

            override fun areContentsTheSame(oldItem: MaterialColorModel, newItem: MaterialColorModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTextStyleBackgroundBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
            viewColor.setBackgroundColor(Color.parseColor(currentItem.colorCode))
            when(currentItem.isSelected){
                true -> ifvSelectedColor.visibility = View.VISIBLE
                false -> ifvSelectedColor.visibility = View.GONE
            }
        }
    }
}