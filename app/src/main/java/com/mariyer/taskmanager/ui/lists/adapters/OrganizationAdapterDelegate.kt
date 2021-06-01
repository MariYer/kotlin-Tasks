package com.mariyer.taskmanager.ui.lists.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.data.db.model.entyties.Organization
import com.mariyer.taskmanager.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_organization.*

class OrganizationAdapterDelegate(
    private val onItemClick: (id: Long) -> Unit,
    private val onDeleteClick: (id: Long) -> Unit
) : AbsListItemAdapterDelegate<Organization, Organization, OrganizationAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: Organization,
        items: MutableList<Organization>,
        position: Int
    ): Boolean {
        return item is Organization
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_organization),onItemClick, onDeleteClick)
    }

    override fun onBindViewHolder(item: Organization, holder: Holder, payloads: MutableList<Any>) {
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

        fun bind(item: Organization) {
            currentId = item.id
            titleTextView.text = item.title
            noteTextView.text = item.notes
        }

    }

}
