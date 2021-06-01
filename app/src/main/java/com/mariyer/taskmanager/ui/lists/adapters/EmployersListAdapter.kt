package com.mariyer.taskmanager.ui.lists.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.mariyer.taskmanager.data.db.model.EmployerStaffWithNames
import com.mariyer.taskmanager.ui.lists.adapters.EmployerAdapterDelegate

class EmployersListAdapter(
    private val onItemClick: (id: Long) -> Unit,
    private val onDeleteClick: (emplId: Long) -> Unit

): AsyncListDifferDelegationAdapter<EmployerStaffWithNames>(EmployerStaffWithNamesDiffUtilCallback())  {

    init {
        delegatesManager.addDelegate(EmployerAdapterDelegate(onItemClick, onDeleteClick))
    }

    class EmployerStaffWithNamesDiffUtilCallback: DiffUtil.ItemCallback<EmployerStaffWithNames>() {
        override fun areItemsTheSame(oldItem: EmployerStaffWithNames, newItem: EmployerStaffWithNames): Boolean {
            return oldItem is EmployerStaffWithNames && newItem is EmployerStaffWithNames && oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EmployerStaffWithNames, newItem: EmployerStaffWithNames): Boolean {
            return oldItem == newItem
        }

    }
}