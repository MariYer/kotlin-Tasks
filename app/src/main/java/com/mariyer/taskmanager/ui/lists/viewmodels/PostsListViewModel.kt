package com.mariyer.taskmanager.ui.lists.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mariyer.taskmanager.data.db.model.entyties.Post
import com.mariyer.taskmanager.data.repositories.PostRepository
import kotlinx.coroutines.launch

class PostsListViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = PostRepository(application)

    var currentOrgId: Long = 0L
        private set

    val posts: LiveData<List<Post>>
        get() = repository.getAllPosts(currentOrgId)
            .asLiveData(viewModelScope.coroutineContext)

    fun setCurrentOrgId(orgId: Long) {
        currentOrgId = orgId
    }

    fun deletePost(orgId: Long, postId: Long) {
        viewModelScope.launch {
            try {
                repository.removePost(postId)
            } catch (t: Throwable) {
                Log.e("PostsListViewModel", "deletePost: ${t.message}")
            }
        }
    }
}