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

    companion object {
        @JvmStatic
        fun newInstance() =
                TaskListFragment().apply {
                    //                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }*/
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
        val taskList = mTaskBusiness.getList(mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID).toInt())

        for(i in 0..50){
            taskList.add(taskList[0].copy(description = "Descricao $i"))
        }

        mRecyclerTaskList.adapter = TaskListAdapter(taskList)
        // 3 - Definir um layout
        mRecyclerTaskList.layoutManager = LinearLayoutManager(mContext)
        //
        return rootView
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.fragTaskListFabAddTask ->{
                startActivity(Intent(mContext,TaskFormActivity::class.java))
            }
        }
    }
}
