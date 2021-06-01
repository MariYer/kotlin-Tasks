package com.mariyer.taskmanager.ui.details.adapters

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.data.db.model.EmployerStaffWithNames
import com.mariyer.taskmanager.utils.inflate
import com.mariyer.taskmanager.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_carrier.*
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class EmployerStaffAdapterDelegate(
    private val OnItemClick: (emplId: Long, depId: Long, postId: Long) -> Unit,
    private val OnDeleteClick: (emplId: Long, depId: Long, postId: Long) -> Unit
) : AbsListItemAdapterDelegate<EmployerStaffWithNames, EmployerStaffWithNames, EmployerStaffAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: EmployerStaffWithNames,
        items: MutableList<EmployerStaffWithNames>,
        position: Int
    ): Boolean {
        return item is EmployerStaffWithNames
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_carrier), OnItemClick, OnDeleteClick)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
        item: EmployerStaffWithNames,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View,
        private val OnItemClick: (emplId: Long, depId: Long, postId: Long) -> Unit,
        private val OnDeleteClick: (emplId: Long, depId: Long, postId: Long) -> Unit
    ): RecyclerView.ViewHolder(containerView), LayoutContainer {
        @RequiresApi(Build.VERSION_CODES.O)
        private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        private var currentEmplId: Long? = null
        private var currentDepId: Long? = null
        private var currentPostId: Long? = null

        init {
            containerView.setOnClickListener {
                currentEmplId?.let { emplId ->
                    currentDepId?.let { depId ->
                        currentPostId?.let { postId ->
                            OnItemClick(emplId, depId, postId)
                        }
                    }
                }
            }

            deleteButton.setOnClickListener {
                currentEmplId?.let { emplId ->
                    currentDepId?.let { depId ->
                        currentPostId?.let { postId ->
                            OnDeleteClick(emplId, depId, postId)
                        }
                    }
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: EmployerStaffWithNames) {
            currentEmplId = item.employerId
            currentDepId = item.departmentId
            currentPostId = item.postId
            departmentTitleTextView.text = item.departmentName
            postTitleTextView.text = item.postName
            startDateTextView.text = item.dateStart.atZone(ZoneId.systemDefault()).format(formatter)
            endDateTextView.text = item.dateEnd?.atZone(ZoneId.systemDefault())?.format(formatter)
        }
    }

}
