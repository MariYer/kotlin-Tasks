package com.mariyer.taskmanager.ui.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.ui.lists.adapters.PostsListAdapter
import com.mariyer.taskmanager.ui.lists.viewmodels.PostsListViewModel
import com.mariyer.taskmanager.utils.AutoClearedValue
import kotlinx.android.synthetic.main.fragment_posts_list.*

class PostsListFragment: Fragment(R.layout.fragment_posts_list) {
    private val viewModel: PostsListViewModel by viewModels()
    private var postsAdapter: PostsListAdapter by AutoClearedValue<PostsListAdapter>()
    private val args: PostsListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setCurrentOrgId(args.orgId)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.title = getString(R.string.captionPostsListFragment)
        init()

        addButton.setOnClickListener {
            val action = PostsListFragmentDirections.actionPostsListFragmentToPostAddFragment(args.orgId)
            findNavController().navigate(action)
        }

        viewModel.posts.observe(viewLifecycleOwner) { postsAdapter.items = it }
    }

    private fun init() {
        postsAdapter = PostsListAdapter( { id ->
            val action = PostsListFragmentDirections.actionPostsListFragmentToPostDetailFragment(id)
            findNavController().navigate(action)
        }, { id ->
            viewModel.deletePost(args.orgId, id)
        })

        with(postsList) {
            adapter = postsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}