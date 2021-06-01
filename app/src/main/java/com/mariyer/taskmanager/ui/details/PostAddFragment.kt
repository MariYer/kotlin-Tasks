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

class PostAddFragment: Fragment(R.layout.fragment_post_detail) {
    private val viewModel: PostDetailViewModel by viewModels<PostDetailViewModel>()
    private val args: PostAddFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.title = getString(R.string.captionPostAddFragment)
        saveButton.setOnClickListener {
            viewModel.savePost(
                Post(
                   id = 0L,
                   organizationId = args.orgId,
                   title = titleEditText.text.toString()
                )
            )
            findNavController().popBackStack()
        }
    }

}