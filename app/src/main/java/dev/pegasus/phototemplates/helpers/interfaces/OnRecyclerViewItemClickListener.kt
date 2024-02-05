package dev.pegasus.phototemplates.helpers.interfaces

import dev.pegasus.phototemplates.helpers.model.RecyclerViewItem

interface OnRecyclerViewItemClickListener {
    fun onItemClick(recyclerViewItem: RecyclerViewItem)
}