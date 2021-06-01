package com.mariyer.taskmanager.ui.lists.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.mariyer.taskmanager.data.db.model.entyties.Organization
import com.mariyer.taskmanager.ui.lists.adapters.OrganizationAdapterDelegate

class OrganizationListAdapter(
    private val onItemClick: (id: Long) -> Unit,
    private val onDeleteClick: (id: Long) -> Unit
): AsyncListDifferDelegationAdapter<Organization>(OrganizationDiffUtilCallback())  {

    init {
        delegatesManager.addDelegate(OrganizationAdapterDelegate(onItemClick, onDeleteClick))
    }

    class OrganizationDiffUtilCallback: DiffUtil.ItemCallback<Organization>() {
        override fun areItemsTheSame(oldItem: Organization, newItem: Organization): Boolean {
            return oldItem is Organization && newItem is Organization && oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Organization, newItem: Organization): Boolean {
            return oldItem == newItem
        }

    }
}