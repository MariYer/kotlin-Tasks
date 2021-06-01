package com.mariyer.taskmanager.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mariyer.taskmanager.R
import com.mariyer.taskmanager.data.db.model.entyties.Post
import com.mariyer.taskmanager.ui.details.viewmodels.PostDetailViewModel
import kotlinx.android.synthetic.main.fragment_post_detail.*

class PostDetailFragment: Fragment(R.layout.fragment_post_detail) {
    private val viewModel: PostDetailViewModel by viewModels<PostDetailViewModel>()
    private val args: PostDetailFragmentArgs by navArgs()
    private var currentPost: Post? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.title = "Информация о должности"

        saveButton.setOnClickListener {
            currentPost?.let {
                it.title = titleEditText.text.toString()
                viewModel.savePost(it)
            }
            findNavController().popBackStack()
        }

        viewModel.post.observe(viewLifecycleOwner) {showPost(it)}
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPostById(args.postId)
    }

    private fun showPost(post: Post?) {
        titleEditText.setText(post?.title)
    }

}