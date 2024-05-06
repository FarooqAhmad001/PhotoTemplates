package dev.pegasus.template.helpers.adapters

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.pegasus.template.R
import dev.pegasus.template.databinding.ItemAdjustLayoutBinding
import dev.pegasus.template.databinding.ItemLayoutTemplatesMainTabsBinding
import dev.pegasus.template.helpers.adapters.AdapterFilters.Companion.filterDiffUtil
import dev.pegasus.template.helpers.dataClasses.RecycleViewModelClass

class AdapterAdjust(
    context: Context,
    private val itemClicked: (position: Int) -> Unit
): ListAdapter<RecycleViewModelClass, AdapterAdjust.CustomViewHolder>(filterDiffUtil) {

    private val selectedTintColor = ContextCompat.getColor(context, R.color.selected_tab_color)
    private val unSelectedTintColor = ContextCompat.getColor(context, R.color.unselected_tab_color)

    inner class CustomViewHolder(val binding: ItemAdjustLayoutBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.mainLayout.setOnClickListener {
                itemClicked.invoke(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAdjustLayoutBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
            ifvAdjustIcon.setImageResource(currentItem.controlIcon)
            mtvAdjustName.text = currentItem.controlName

            when(currentItem.isSelected){
                true -> {
                    ifvAdjustIcon.colorFilter = PorterDuffColorFilter(selectedTintColor, PorterDuff.Mode.SRC_IN)
                    mtvAdjustName.setTextColor(selectedTintColor)
                }
                false -> {
                    ifvAdjustIcon.colorFilter = PorterDuffColorFilter(unSelectedTintColor, PorterDuff.Mode.SRC_IN)
                    mtvAdjustName.setTextColor(unSelectedTintColor)
                }
            }
        }
    }

}