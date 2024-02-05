package dev.pegasus.phototemplates.helpers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.pegasus.phototemplates.R
import dev.pegasus.phototemplates.databinding.ItemFontBinding
import dev.pegasus.phototemplates.helpers.interfaces.OnFontItemClickListener
import dev.pegasus.phototemplates.helpers.model.Font

class AdapterCollageFonts(private val onFontItemClickListener: OnFontItemClickListener) : ListAdapter<Font, AdapterCollageFonts.ViewHolderFonts>(diffUtilFonts) {

    companion object {
        val diffUtilFonts = object : DiffUtil.ItemCallback<Font>() {
            override fun areItemsTheSame(oldItem: Font, newItem: Font): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Font, newItem: Font): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolderFonts(val binding: ItemFontBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFonts {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemFontBinding>(layoutInflater, R.layout.item_font, parent, false)
        return ViewHolderFonts(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderFonts, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
            item = currentItem
            itemClick = onFontItemClickListener
        }
    }
}