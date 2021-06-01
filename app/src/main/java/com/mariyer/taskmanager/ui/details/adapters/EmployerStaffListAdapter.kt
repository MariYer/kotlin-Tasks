package com.mariyer.taskmanager.ui.details.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.mariyer.taskmanager.data.db.model.EmployerStaffWithNames

class EmployerStaffListAdapter(
    private val OnItemClick: (emplId: Long, depId: Long, postId: Long) -> Unit,
    private val OnDeleteClick: (emplId: Long, depId: Long, postId: Long) -> Unit
): AsyncListDifferDelegationAdapter<EmployerStaffWithNames>(EmployerStaffDiffUtilCallback())  {

    init {
        delegatesManager.addDelegate(EmployerStaffAdapterDelegate(OnItemClick, OnDeleteClick))
    }

    class EmployerStaffDiffUtilCallback: DiffUtil.ItemCallback<EmployerStaffWithNames>() {
        override fun areItemsTheSame(oldItem: EmployerStaffWithNames, newItem: EmployerStaffWithNames): Boolean {
            return oldItem is EmployerStaffWithNames && newItem is EmployerStaffWithNames && oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EmployerStaffWithNames, newItem: EmployerStaffWithNames): Boolean {
            return oldItem == newItem
        }

    }
}