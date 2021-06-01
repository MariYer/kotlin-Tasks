package com.mariyer.taskmanager.ui.lists.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.data.db.model.EmployerStaffWithNames
import com.mariyer.taskmanager.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_employer.*

class EmployerAdapterDelegate(
    private val onItemClick: (id: Long) -> Unit,
    private val onDeleteClick: (id: Long) -> Unit
) : AbsListItemAdapterDelegate<EmployerStaffWithNames, EmployerStaffWithNames, EmployerAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: EmployerStaffWithNames,
        items: MutableList<EmployerStaffWithNames>,
        position: Int
    ): Boolean {
        return item is EmployerStaffWithNames
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_employer), onItemClick, onDeleteClick)
    }

    override fun onBindViewHolder(item: EmployerStaffWithNames, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View,
        private val onItemClick: (id: Long) -> Unit,
        private val onDeleteClick: (emplId: Long) -> Unit
    ): RecyclerView.ViewHolder(containerView), LayoutContainer {
        private var currentId: Long? = null
        private var currentEmplId: Long? = null

        init {
            containerView.setOnClickListener {
                currentId?.let { id ->
                    onItemClick(id)
                 }
            }
            deleteButton.setOnClickListener {
                currentEmplId?.let { emplId ->
                    onDeleteClick(emplId)
                }
            }
        }

        fun bind(item: EmployerStaffWithNames) {
            currentId = item.id
            currentEmplId = item.employerId
            fullNameTextView.text = item.fullEmployerName
            departmentTextView.text = item.departmentName
            postTextView.text = item.postName
        }

    }

}
