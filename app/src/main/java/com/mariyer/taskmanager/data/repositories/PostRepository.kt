package com.mariyer.taskmanager.data.repositories

import android.content.Context
import com.mariyer.taskmanager.data.db.Database
import com.mariyer.taskmanager.data.db.model.entyties.Post
import kotlinx.coroutines.flow.Flow

class PostRepository(context: Context) {
    private val postDao = Database.instance.postDao()

    fun getAllPosts(orgId: Long): Flow<List<Post>> {
        return postDao.getPostsByOrgId(orgId)
    }

    suspend fun getPostById(postId: Long): Post? {
        return postDao.getPostById(postId)
    }

    suspend fun savePost(post: Post) {
        postDao.insertPosts(listOf(post))
    }

    suspend fun updatePost(post: Post) {
        postDao.updatePost(post)
    }

    suspend fun removePost(postId: Long) {
        postDao.removePost(postId)
    }
}