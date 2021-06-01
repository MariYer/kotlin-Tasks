package com.mariyer.taskmanager.ui.lists.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.mariyer.taskmanager.data.db.model.StaffTableWithNames

class StaffTableListAdapter(
    private val onItemClick: (depId: Long, postId: Long) -> Unit,
    private val onDeleteClick: (depId: Long, postId: Long) -> Unit
) : AsyncListDifferDelegationAdapter<StaffTableWithNames>(StaffTableDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(StaffTableAdapterDelegate(onItemClick, onDeleteClick))
    }

    class StaffTableDiffUtilCallback : DiffUtil.ItemCallback<StaffTableWithNames>() {
        override fun areItemsTheSame(
            oldItem: StaffTableWithNames,
            newItem: StaffTableWithNames
        ): Boolean {
            return oldItem is StaffTableWithNames && newItem is StaffTableWithNames &&
                   oldItem.departmentId == newItem.departmentId && oldItem.postId == newItem.postId
        }

        override fun areContentsTheSame(
            oldItem: StaffTableWithNames,
            newItem: StaffTableWithNames
        ): Boolean {
            return oldItem == newItem
        }

    }
}