package com.mariyer.taskmanager.ui.lists.adapters

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.data.db.model.TaskWithNames
import com.mariyer.taskmanager.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_task.*
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TaskAdapterDelegate(
    private val onItemClick: (id: Long, orgId: Long) -> Unit,
    private val onDeleteClick: (id: Long, projId: Long) -> Unit
) : AbsListItemAdapterDelegate<TaskWithNames, TaskWithNames, TaskAdapterDelegate.Holder>() {

     override fun isForViewType(
         item: TaskWithNames,
         items: MutableList<TaskWithNames>,
         position: Int
    ): Boolean {
        return item is TaskWithNames
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_task), onItemClick, onDeleteClick)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(item: TaskWithNames, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View,
        private val onItemClick: (id: Long, orgId: Long) -> Unit,
        private val onDeleteClick: (id: Long, projId: Long) -> Unit
    ): RecyclerView.ViewHolder(containerView), LayoutContainer {
        @RequiresApi(Build.VERSION_CODES.O)
        private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        private var currentId: Long? = null
        private var currentOrgId: Long? = null
        private var currentProjId: Long? = null

        init {
            containerView.setOnClickListener {
                currentId?.let { id ->
                    currentOrgId?.let { orgId ->
                        onItemClick(id, orgId)
                    }
                }
            }

            deleteButton.setOnClickListener {
                currentId?.let { id ->
                    currentProjId?.let { projId ->
                        onDeleteClick(id, projId)
                    }
                }
            }
        }
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: TaskWithNames) {
            currentId = item.id
            currentOrgId = item.organizationId
            currentProjId = item.projectId
            taskTitleTextView.text = item.title
            taskDeadlineTextView.text = item.deadline.atZone(ZoneId.systemDefault()).format(formatter)
            taskStatusTextView.text = item.stateTitle
            executorNameTextView.text = item.executorName
        }

    }
}
