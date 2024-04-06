package dev.pegasus.template.helpers.adapters

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.marginStart
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.pegasus.template.R
import dev.pegasus.template.helpers.dataClasses.TemplateMainTabsModel
import dev.pegasus.template.databinding.ItemLayoutTemplatesMainTabsBinding
import java.lang.ref.WeakReference

class AdapterTemplateMainTabs(
    context: Context,
    private val itemWidth: Int,
    private val itemClickedCallback: (position: Int) -> Unit
) : ListAdapter<TemplateMainTabsModel, AdapterTemplateMainTabs.CustomViewHolder>(diffUtil) {


    // Set the tint color
    private val selectedTintColor = ContextCompat.getColor(context, R.color.selected_tab_color)
    private val unSelectedTintColor = ContextCompat.getColor(context, R.color.unselected_tab_color)

    inner class CustomViewHolder(val binding: ItemLayoutTemplatesMainTabsBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.layoutParams.width = itemWidth
            binding.mainLayout.setOnClickListener {
                itemClickedCallback.invoke(absoluteAdapterPosition)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TemplateMainTabsModel>() {
            override fun areItemsTheSame(oldItem: TemplateMainTabsModel, newItem: TemplateMainTabsModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TemplateMainTabsModel, newItem: TemplateMainTabsModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ItemLayoutTemplatesMainTabsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
            ifvIcon.setImageResource(currentItem.iconResourceId)
            mtvTabName.text = currentItem.tabName

            when(currentItem.isSelected){
                true -> {
                    ifvIcon.colorFilter = PorterDuffColorFilter(selectedTintColor, PorterDuff.Mode.SRC_IN)
                    mtvTabName.setTextColor(selectedTintColor)
                }
                false -> {
                    ifvIcon.colorFilter = PorterDuffColorFilter(unSelectedTintColor, PorterDuff.Mode.SRC_IN)
                    mtvTabName.setTextColor(unSelectedTintColor)
                }
            }
        }
    }

}