package dev.pegasus.template.helpers.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.pegasus.template.R
import dev.pegasus.template.databinding.ItemFiltersLayoutBinding
import dev.pegasus.template.helpers.dataClasses.RecycleViewModelClass

class AdapterFilters(
    context: Context? = null,
    private val filterClickedCallback: (position: Int) -> Unit
): ListAdapter<RecycleViewModelClass, AdapterFilters.CustomViewHolder>(filterDiffUtil) {

    private val selectedItemBorder = context?.let { ContextCompat.getDrawable(it, R.drawable.bg_rectangle_border_of_filter) }

    inner class CustomViewHolder(val binding: ItemFiltersLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        init {
                binding.mainLayout.setOnClickListener {
                    filterClickedCallback.invoke(absoluteAdapterPosition)
                }
        }
    }

    companion object {
        val filterDiffUtil = object : DiffUtil.ItemCallback<RecycleViewModelClass>() {
            override fun areItemsTheSame(oldItem: RecycleViewModelClass, newItem: RecycleViewModelClass): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RecycleViewModelClass, newItem: RecycleViewModelClass): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFiltersLayoutBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
            ifvFilter.setImageResource(currentItem.controlIcon)
            mtvFilterName.text = currentItem.controlName

            when(currentItem.isSelected){
                true -> ifvFilter.foreground = selectedItemBorder
                false -> ifvFilter.foreground = null
            }
        }
    }
}