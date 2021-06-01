package com.mariyer.taskmanager.ui.lists.adapters

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.data.db.model.DepartmentWithHierarchy
import com.mariyer.taskmanager.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_department.*

class DepartmentAdapterDelegate(
    private val OnItemClick: (id: Long) -> Unit,
    private val OnDeleteClick: (id: Long) -> Unit

) : AbsListItemAdapterDelegate<DepartmentWithHierarchy, DepartmentWithHierarchy, DepartmentAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: DepartmentWithHierarchy,
        items: MutableList<DepartmentWithHierarchy>,
        position: Int
    ): Boolean {
        return item is DepartmentWithHierarchy
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_department),OnItemClick, OnDeleteClick)
    }

    override fun onBindViewHolder(item: DepartmentWithHierarchy, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View,
        private val onItemClick: (id: Long) -> Unit,
        private val onDeleteClick: (id: Long) -> Unit

    ): RecyclerView.ViewHolder(containerView), LayoutContainer {
        private var currentId: Long? = null

        init {
            containerView.setOnClickListener {
                currentId?.let { id ->
                    onItemClick(id)
                }
            }
            deleteButton.setOnClickListener {
                currentId?.let { id ->
                    onDeleteClick(id)
                }
            }
        }

        fun bind(item: DepartmentWithHierarchy) {
            currentId = item.id
            titleTextView.text = item.hierTitle
            if (item.parentId?:0L == 0L) {
                departmentLayout.setBackgroundColor(Color.rgb(141, 250, 152))
                titleTextView.setTextColor(Color.WHITE)
            }
        }

    }

}
