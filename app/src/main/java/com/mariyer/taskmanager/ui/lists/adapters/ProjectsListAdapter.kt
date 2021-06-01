package com.mariyer.taskmanager.ui.lists.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.mariyer.taskmanager.data.db.model.ProjectWithOrgDepartment

class ProjectsListAdapter(
    private val onItemClick: (id: Long, orgId: Long) -> Unit,
    private val onDeleteClick: (id: Long) -> Unit
) : AsyncListDifferDelegationAdapter<ProjectWithOrgDepartment>(ProjectDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ProjectAdapterDelegate(onItemClick, onDeleteClick))
    }

    class ProjectDiffUtilCallback : DiffUtil.ItemCallback<ProjectWithOrgDepartment>() {
        override fun areItemsTheSame(
            oldItem: ProjectWithOrgDepartment,
            newItem: ProjectWithOrgDepartment
        ): Boolean {
            return oldItem is ProjectWithOrgDepartment && newItem is ProjectWithOrgDepartment && oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProjectWithOrgDepartment,
            newItem: ProjectWithOrgDepartment
        ): Boolean {
            return oldItem == newItem
        }

    }
}