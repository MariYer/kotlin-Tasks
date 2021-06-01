package com.mariyer.taskmanager.ui.lists.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.mariyer.taskmanager.data.db.model.entyties.Post

class PostsListAdapter(
    private val onItemClick: (id: Long) -> Unit,
    private val onDeleteClick: (id: Long) -> Unit
): AsyncListDifferDelegationAdapter<Post>(PostDiffUtilCallback())  {

    init {
        delegatesManager.addDelegate(PostAdapterDelegate(onItemClick, onDeleteClick))
    }

    class PostDiffUtilCallback: DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem is Post && newItem is Post && oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

    }
}