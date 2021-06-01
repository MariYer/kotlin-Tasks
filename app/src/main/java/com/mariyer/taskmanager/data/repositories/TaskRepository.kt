package com.mariyer.taskmanager.data.repositories

import android.content.Context
import com.mariyer.taskmanager.data.db.Database
import com.mariyer.taskmanager.data.db.model.TaskWithNames
import com.mariyer.taskmanager.data.db.model.entyties.Task
import com.mariyer.taskmanager.data.db.model.entyties.TaskState
import kotlinx.coroutines.flow.Flow

class TaskRepository(context: Context) {
    private val taskDao = Database.instance.taskDao()
    private val taskStateDao = Database.instance.taskStateDao()

    suspend fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks()
    }

    fun getTasksByProjectId(projectId: Long): Flow<List<TaskWithNames>> {
        return taskDao.getTasksByProjectId(projectId)
    }

    suspend fun getTaskById(taskId: Long): TaskWithNames? {
        return taskDao.getTaskById(taskId)
    }

    suspend fun saveTask(task: Task) {
        taskDao.insertTasks(listOf(task))
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun removeTask(taskId: Long) {
        taskDao.removeTask(taskId)
    }

    suspend fun fillStates() {
        taskStateDao.insertTaskStates(
            listOf(
                TaskState(1, "Подготовлена"),
                TaskState(2, "Согласована"),
                TaskState(3, "На исполнении"),
                TaskState(4, "На тестировании"),
                TaskState(5, "Завершена")
            )
        )

    }
    fun getAllStates(): Flow<List<TaskState>> {
        return taskStateDao.getAllTaskStates()
    }

    fun searchItemById(list: List<String>, searchId: Long): String? {
        return list.find { item ->
            item.contains("(id=${searchId.toString()})")
        }
    }
}