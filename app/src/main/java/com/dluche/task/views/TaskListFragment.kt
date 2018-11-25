package com.dluche.task.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dluche.task.R


class TaskListFragment : Fragment(), View.OnClickListener {

    private lateinit var mContext: Context

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
        rootView.findViewById<FloatingActionButton>(R.id.fragTaskListFabAddTask).setOnClickListener(this)
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
