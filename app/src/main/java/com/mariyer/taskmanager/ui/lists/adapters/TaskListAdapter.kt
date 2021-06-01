package com.mariyer.taskmanager.ui.lists.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.mariyer.taskmanager.data.db.model.TaskWithNames

class TaskListAdapter(
    private val onItemClick: (id: Long, orgId: Long) -> Unit,
    private val oOnDeleteClick: (id: Long, projId: Long) -> Unit
) : AsyncListDifferDelegationAdapter<TaskWithNames>(TaskDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(TaskAdapterDelegate(onItemClick, oOnDeleteClick))
    }

    class TaskDiffUtilCallback: DiffUtil.ItemCallback<TaskWithNames>() {
        override fun areItemsTheSame(oldItem: TaskWithNames, newItem: TaskWithNames): Boolean {
            return oldItem is TaskWithNames && newItem is TaskWithNames && oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TaskWithNames, newItem: TaskWithNames): Boolean {
            return oldItem == newItem
        }

    }

}
