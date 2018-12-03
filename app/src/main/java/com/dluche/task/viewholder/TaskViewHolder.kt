package com.dluche.task.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.dluche.task.R
import com.dluche.task.entities.TaskEntity
import com.dluche.task.repository.PriorityCacheConstants

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    private val mImageComplete: ImageView = itemView.findViewById(R.id.rowTaskListIvComplete)
    private val mTextDescription: TextView = itemView.findViewById(R.id.rowTaskListTvDesc)
    private val mTextPriority: TextView = itemView.findViewById(R.id.rowTaskListTvPriority)
    private val mTextDuedate: TextView = itemView.findViewById(R.id.rowTaskListTvDuedate)

    fun bindData(task: TaskEntity){
        mTextDescription.text = task.description
        mTextPriority.text = PriorityCacheConstants.gePriorityDescription(task.id)
        mTextDuedate.text = task.dueDate
        //
        if(task.complete){
            mImageComplete.setImageResource(R.drawable.ic_done_black_24dp)
        }else{
            mImageComplete.setImageResource(R.drawable.ic_todo_black_24dp)
        }
    }
}