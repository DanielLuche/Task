package com.dluche.task.business

import android.content.Context
import com.dluche.task.entities.PriorityEntity
import com.dluche.task.repository.PriorityRepository

class PriorityBusiness(context: Context) {

    private val mPriorityRepository : PriorityRepository = PriorityRepository.getInstance(context)
    //
    fun getList(): MutableList<PriorityEntity> = mPriorityRepository.getList()

}