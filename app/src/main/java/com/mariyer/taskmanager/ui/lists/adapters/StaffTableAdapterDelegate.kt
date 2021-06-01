package com.mariyer.taskmanager.ui.lists.adapters

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.data.db.model.StaffTableWithNames
import com.mariyer.taskmanager.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_staff_table.*

class StaffTableAdapterDelegate(
    private val onItemClick: (depId: Long, postId: Long) -> Unit,
    private val onDeleteClick: (depId: Long, postId: Long) -> Unit
) : AbsListItemAdapterDelegate<StaffTableWithNames, StaffTableWithNames, StaffTableAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: StaffTableWithNames,
        items: MutableList<StaffTableWithNames>,
        position: Int
    ): Boolean {
        return item is StaffTableWithNames
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_staff_table), onItemClick, onDeleteClick)
    }

    override fun onBindViewHolder(
        item: StaffTableWithNames,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View,
        private val onItemClick: (depId: Long, postId: Long) -> Unit,
        private val onDeleteClick: (depId: Long, postId: Long) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        private var currentDepId: Long? = null
        private var currentPostId: Long? = null

        init {
            containerView.setOnClickListener {
                currentDepId?.let { depId ->
                    currentPostId?.let { postId ->
                        onItemClick(depId, postId)
                    }
                }
            }

            deleteButton.setOnClickListener {
                currentDepId?.let { depId ->
                    currentPostId?.let { postId ->
                        onDeleteClick(depId, postId)
                    }
                }
            }
        }

        fun bind(item: StaffTableWithNames) {
            currentDepId = item.departmentId
            currentPostId = item.postId
            departmentTitleTextView.text =item.departmentTitle
            postTitleTextView.text = item.postTitle
            maxCountTextView.text = item.maxCount.toString()
            basicSalaryTextView.text = item.basicSalary.toString()

            if (item.isChief) {
                staffTableLayout.setBackgroundColor(Color.YELLOW)
            }
        }

    }

}
