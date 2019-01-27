package com.dluche.task.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dluche.task.R
import com.dluche.task.adapter.TaskListAdapter
import com.dluche.task.business.TaskBusiness
import com.dluche.task.constants.TaskConstants
import com.dluche.task.util.SecurityPreferences


class TaskListFragment : Fragment(), View.OnClickListener {

    private lateinit var mContext: Context
    private lateinit var mRecyclerTaskList: RecyclerView
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences
    private var mTaskFilter = 0

    companion object {
        @JvmStatic
        fun newInstance(taskFilter: Int) =
                TaskListFragment().apply {
                         arguments = Bundle().apply {
                         putInt(TaskConstants.TASKFILTER.KEY, taskFilter)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //if abaixo explicado no curso
//        if(arguments != null){
//             mTaskFilter = arguments?.getInt(TaskConstants.TASKFILTER.KEY)
//        }
        //codigo melhorada pelo novo plugin kotlin
        arguments?.let {
            mTaskFilter = it.getInt(TaskConstants.TASKFILTER.KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)
        mContext = rootView.context
        //
        mTaskBusiness = TaskBusiness(mContext)
        mSecurityPreferences = SecurityPreferences(mContext)
        //
        rootView.findViewById<FloatingActionButton>(R.id.fragTaskListFabAddTask).setOnClickListener(this)
        // 1 - Obter o elemento
        mRecyclerTaskList = rootView.findViewById(R.id.fragTaskListRvTaskList)
        // 2 - Definir um com.dluche.task.adapter com item
        //inicia adapter com lista vazia, pois será carregada no onResume
        mRecyclerTaskList.adapter = TaskListAdapter(mutableListOf())
        // 3 - Definir um layout
        mRecyclerTaskList.layoutManager = LinearLayoutManager(mContext)
        //
        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.fragTaskListFabAddTask ->{
                startActivity(Intent(mContext,TaskFormActivity::class.java))
            }
        }
    }

    private fun loadTasks() {
        mRecyclerTaskList.adapter = TaskListAdapter(mTaskBusiness.getList())
    }
}
