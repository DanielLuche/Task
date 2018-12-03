package com.dluche.task.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dluche.task.R
import com.dluche.task.entities.TaskEntity
import com.dluche.task.viewholder.TaskViewHolder

class TaskListAdapter(val taskList: List<TaskEntity>) : RecyclerView.Adapter<TaskViewHolder>() {

    /**
     * Vars abaixo, criadas para exemplificar a diferença e vantagem entre o Recycler View x List view
     * No caso do list view, os layout não são reaproveitos e são sempre recriados, tem custo elevado
     * por causa do findViewById.
     * Aqui, são criado X layout, para cobrirem apenas a tela e uns a mais, depois eles são
     * reaproveitados e tem seus dados modificados pelo ViewHolder
     *
     */
    private var mCountCreate = 0 // Qtd de vezes que criou layout
    private var mCountBind = 0 // qtd de vezes o bind, vinculo , entre layout e dados

    override fun onBindViewHolder(viewholder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        //
        viewholder.bindData(task)
        mCountBind++
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): TaskViewHolder {
        val context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_task_list,parent,false)
        mCountCreate++
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.count()
    }


}