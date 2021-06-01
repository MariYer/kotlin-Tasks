package com.mariyer.taskmanager.ui.lists.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.data.db.model.ProjectWithOrgDepartment
import com.mariyer.taskmanager.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_project.*

class ProjectAdapterDelegate(
    private val onItemClick: (id: Long, orgId: Long) -> Unit,
    private val onDeleteClick: (id: Long) -> Unit
) : AbsListItemAdapterDelegate<ProjectWithOrgDepartment, ProjectWithOrgDepartment, ProjectAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: ProjectWithOrgDepartment,
        items: MutableList<ProjectWithOrgDepartment>,
        position: Int
    ): Boolean {
        return item is ProjectWithOrgDepartment
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_project), onItemClick, onDeleteClick)
    }

    override fun onBindViewHolder(item: ProjectWithOrgDepartment, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View,
        private val onItemClick: (id: Long, orgId: Long) -> Unit,
        private val onDeleteClick: (id: Long) -> Unit
    ): RecyclerView.ViewHolder(containerView), LayoutContainer {
        private var currentId: Long? = null
        private var currentOrgId: Long? = null

        init {
            containerView.setOnClickListener {
                currentId?.let { projId ->
                    currentOrgId?.let {orgId ->
                        onItemClick(projId,orgId)
                    }
                }
            }

            deleteButton.setOnClickListener {
                currentId?.let { id ->
                    onDeleteClick(id)
                }
            }
        }

        fun bind(item: ProjectWithOrgDepartment) {
            currentId = item.id
            currentOrgId = item.organizationId
            titleTextView.text = item.title
            organizationTextView.text = item.organizationTitle
            departmentTextView.text = item.departamentTitle
        }

    }

}
