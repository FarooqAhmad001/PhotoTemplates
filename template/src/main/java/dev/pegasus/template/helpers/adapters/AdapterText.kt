package dev.pegasus.template.helpers.adapters

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.pegasus.template.R
import dev.pegasus.template.databinding.ItemAdjustLayoutBinding
import dev.pegasus.template.databinding.ItemLayoutTemplatesMainTabsBinding
import dev.pegasus.template.databinding.ItemTextControlsLayoutBinding
import dev.pegasus.template.helpers.dataClasses.RecycleViewModelClass

class AdapterText(
    context: Context,
    private val itemWidth: Int,
    private val itemClicked: (position: Int) -> Unit
): ListAdapter<RecycleViewModelClass, AdapterText.CustomViewHolder>(diffUtil) {

    // Set the tint color
    private val selectedTintColor = ContextCompat.getColor(context, R.color.white_color)
    private val unSelectedTintColor = ContextCompat.getColor(context, R.color.unselected_tab_color)

    inner class CustomViewHolder(val binding: ItemTextControlsLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.layoutParams.width = itemWidth
            binding.mainLayout.setOnClickListener {
                itemClicked.invoke(absoluteAdapterPosition)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<RecycleViewModelClass>() {
            override fun areItemsTheSame(oldItem: RecycleViewModelClass, newItem: RecycleViewModelClass): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RecycleViewModelClass, newItem: RecycleViewModelClass): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ItemTextControlsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    /**
     * Since, adjust item layout is used. so the name of views are different
     */

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
            ifvTextIcon.setImageResource(currentItem.controlIcon)
            mtvTextName.text = currentItem.controlName

            when(currentItem.isSelected){
                true -> {
                    if (currentItem.id == 3) ifvTextIcon.colorFilter = null
                    else ifvTextIcon.colorFilter = PorterDuffColorFilter(selectedTintColor, PorterDuff.Mode.SRC_IN)

                    mtvTextName.setTextColor(selectedTintColor)
                }
                false -> {
                    if (currentItem.id == 3) ifvTextIcon.colorFilter = null
                    else ifvTextIcon.colorFilter = PorterDuffColorFilter(unSelectedTintColor, PorterDuff.Mode.SRC_IN)

                    mtvTextName.setTextColor(unSelectedTintColor)
                }
            }
        }
    }

}