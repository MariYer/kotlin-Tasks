package com.mariyer.taskmanager.ui.lists.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.data.db.model.entyties.Post
import com.mariyer.taskmanager.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_post.*

class PostAdapterDelegate(
    private val onItemClick: (id: Long) -> Unit,
    private val onDeleteClick: (id: Long) -> Unit
) : AbsListItemAdapterDelegate<Post, Post, PostAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: Post,
        items: MutableList<Post>,
        position: Int
    ): Boolean {
        return item is Post
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_post), onItemClick, onDeleteClick)
    }

    override fun onBindViewHolder(item: Post, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View,
        private val onItemClick: (id: Long) -> Unit,
        private val onDeleteClick: (id: Long) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
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

        fun bind(item: Post) {
            currentId = item.id
            titleTextView.text = item.title
        }

    }

}
