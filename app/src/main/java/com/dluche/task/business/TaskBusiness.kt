package com.dluche.task.business

import android.content.Context
import com.dluche.task.entities.TaskEntity
import com.dluche.task.repository.TaskRepository

class TaskBusiness(context: Context) {

    private val mTaskRepository : TaskRepository = TaskRepository.getInstance(context)
    //
    fun getList(userId: Int): MutableList<TaskEntity> = mTaskRepository.getList(userId)

    fun insert(taskEntity: TaskEntity){
        mTaskRepository.insert(taskEntity)
    }

}