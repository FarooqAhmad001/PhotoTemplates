package dev.pegasus.phototemplates.helpers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.pegasus.phototemplates.R
import dev.pegasus.phototemplates.databinding.ItemRecyclerviewBinding
import dev.pegasus.phototemplates.helpers.extensions.gone
import dev.pegasus.phototemplates.helpers.extensions.visible
import dev.pegasus.phototemplates.helpers.interfaces.OnRecyclerViewItemClickListener
import dev.pegasus.phototemplates.helpers.model.RecyclerViewItem

class AdapterRecyclerView(private val onRecyclerViewItemClickListener: OnRecyclerViewItemClickListener, private val caseType: Int = 0) : ListAdapter<RecyclerViewItem, AdapterRecyclerView.CustomViewHolder>(customDiffUtils) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemRecyclerviewBinding>(layoutInflater, R.layout.item_recyclerview, parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
            item = currentItem
            itemClick = onRecyclerViewItemClickListener

            if (caseType == 0) {
                mtvTitleItemRecyclerView.visible()
                ifvImageItemRecyclerView.gone()
            } else if (caseType == 1) {
                mtvTitleItemRecyclerView.gone()
                ifvImageItemRecyclerView.visible()
                if (currentItem.selector) {
                    sivTickItemRecyclerView.visible()
                    ifvImageItemRecyclerView.foreground = ContextCompat.getDrawable(ifvImageItemRecyclerView.context, R.drawable.bg_round_outline_square)
                } else {
                    sivTickItemRecyclerView.gone()
                    ifvImageItemRecyclerView.foreground = null
                }
            }
        }
    }

    inner class CustomViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val customDiffUtils = object : DiffUtil.ItemCallback<RecyclerViewItem>() {
            override fun areItemsTheSame(oldItem: RecyclerViewItem, newItem: RecyclerViewItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RecyclerViewItem, newItem: RecyclerViewItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}