package com.mariyer.taskmanager.ui.lists.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.mariyer.taskmanager.data.db.model.DepartmentWithHierarchy
import com.mariyer.taskmanager.ui.lists.adapters.DepartmentAdapterDelegate

class DepartmentsListAdapter(
    private val onItemClick: (id: Long) -> Unit,
    private val onDeleteClick: (id: Long) -> Unit

): AsyncListDifferDelegationAdapter<DepartmentWithHierarchy>(DepartmentDiffUtilCallback())  {

    init {
        delegatesManager.addDelegate(DepartmentAdapterDelegate(onItemClick, onDeleteClick))
    }

    class DepartmentDiffUtilCallback: DiffUtil.ItemCallback<DepartmentWithHierarchy>() {
        override fun areItemsTheSame(oldItem: DepartmentWithHierarchy, newItem: DepartmentWithHierarchy): Boolean {
            return oldItem is DepartmentWithHierarchy && newItem is DepartmentWithHierarchy && oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DepartmentWithHierarchy, newItem: DepartmentWithHierarchy): Boolean {
            return oldItem == newItem
        }

    }
}