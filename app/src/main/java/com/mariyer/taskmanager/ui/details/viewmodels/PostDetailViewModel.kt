package com.mariyer.taskmanager.ui.details.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mariyer.taskmanager.data.db.model.entyties.Post
import com.mariyer.taskmanager.data.repositories.PostRepository
import kotlinx.coroutines.launch

class PostDetailViewModel(application: Application
) : AndroidViewModel(application) {

    private val repository = PostRepository(application)

    private val postMutableData = MutableLiveData<Post>()
    val post: LiveData<Post>
        get() = postMutableData

    fun getPostById(postId: Long) {
        viewModelScope.launch {
            try {
                postMutableData.postValue(repository.getPostById(postId))
            } catch (t: Throwable) {

            }
        }
    }

    fun savePost(post: Post) {
        viewModelScope.launch {
            try {
                if (post.id == 0L) {
                    repository.savePost(post)
                } else {
                    repository.updatePost(post)
                }
            } catch (t: Throwable) {

            }
        }
    }
}
