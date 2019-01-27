package com.dluche.task.business

import android.content.Context
import com.dluche.task.constants.TaskConstants
import com.dluche.task.entities.TaskEntity
import com.dluche.task.repository.TaskRepository
import com.dluche.task.util.SecurityPreferences

class TaskBusiness(context: Context) {

    private val mTaskRepository : TaskRepository = TaskRepository.getInstance(context)
    private val mSecurityPreferences : SecurityPreferences = SecurityPreferences(context)
    //
    fun getList(): MutableList<TaskEntity> {
        val userId =  mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID).toInt()
        return mTaskRepository.getList(userId)
    }

    fun insert(taskEntity: TaskEntity){
        mTaskRepository.insert(taskEntity)
    }

}