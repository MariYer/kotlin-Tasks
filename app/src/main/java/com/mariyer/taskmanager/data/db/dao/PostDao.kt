package com.mariyer.taskmanager.data.db.dao

import androidx.room.*
import com.mariyer.taskmanager.data.db.contracts.PostContract
import com.mariyer.taskmanager.data.db.model.entyties.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM ${PostContract.TABLE_NAME}")
    suspend fun getAllPosts(): List<Post>

    @Query("SELECT * FROM ${PostContract.TABLE_NAME} WHERE ${PostContract.Columns.ORGANIZATION_ID} = :orgId")
    fun getPostsByOrgId(orgId: Long): Flow<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Post>)

    @Update
    suspend fun updatePost(post: Post)

    @Query("DELETE FROM ${PostContract.TABLE_NAME} WHERE ${PostContract.Columns.ID} = :postId")
    suspend fun removePost(postId: Long)

    @Query("SELECT * FROM ${PostContract.TABLE_NAME} WHERE ${PostContract.Columns.ID} = :postId")
    suspend fun getPostById(postId: Long): Post?

}