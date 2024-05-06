package dev.pegasus.template.helpers.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.pegasus.template.R
import dev.pegasus.template.databinding.ItemTextViewLayoutBinding
import dev.pegasus.template.helpers.dataClasses.FontModel

class AdapterFonts(
    context: Context,
    private val itemClicked: (position: Int) -> Unit
): ListAdapter<FontModel, AdapterFonts.CustomViewHolder>(diffUtil) {

    private val selectedFontColor = ContextCompat.getColor(context, R.color.selected_tab_color)
    private val unSelectedFontColor = ContextCompat.getColor(context, R.color.unselected_tab_color)

    inner class CustomViewHolder(val binding: ItemTextViewLayoutBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.mtvFontName.setOnClickListener {
                itemClicked.invoke(absoluteAdapterPosition)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<FontModel>() {
            override fun areItemsTheSame(oldItem: FontModel, newItem: FontModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FontModel, newItem: FontModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTextViewLayoutBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
            mtvFontName.text = currentItem.name

            when(currentItem.isSelected){
                true -> mtvFontName.setTextColor(selectedFontColor)
                false -> mtvFontName.setTextColor(unSelectedFontColor)
            }
        }
    }


}