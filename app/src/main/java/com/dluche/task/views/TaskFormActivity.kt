package com.dluche.task.views

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.dluche.task.R
import com.dluche.task.business.PriorityBusiness
import com.dluche.task.business.TaskBusiness
import com.dluche.task.constants.TaskConstants
import com.dluche.task.entities.PriorityEntity
import com.dluche.task.entities.TaskEntity
import com.dluche.task.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_task_form.*
import java.text.SimpleDateFormat
import java.util.*

class TaskFormActivity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private lateinit var mPriorityBusiness : PriorityBusiness
    private lateinit var mTaskBusiness : TaskBusiness
    private lateinit var mSecurityPreferences : SecurityPreferences
    private val mSimpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    private var mLstPriorityEntity: MutableList<PriorityEntity> = mutableListOf()
    private var mLstPriorityId: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)
        //
        iniVars()
        //
        setListeners()
        //
        loadPriorities()

    }

    override fun onClick(v: View) {
        when(v.id)  {
            R.id.actTaskFormBtnDate->{
                openDatePickerDialog()
            }
            R.id.actTaskFormBtnSave->{
                handleSave()
            }
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year,month,dayOfMonth)
        actTaskFormBtnDate.text = mSimpleDateFormat.format(calendar.time)
    }

    private fun setListeners() {
        actTaskFormBtnDate.setOnClickListener(this)
        actTaskFormBtnSave.setOnClickListener(this)
    }

    private fun iniVars() {
        mPriorityBusiness = PriorityBusiness(this)
        mTaskBusiness = TaskBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)
    }

    private fun loadPriorities() {
        mLstPriorityEntity = mPriorityBusiness.getList()
        val lstPriorities = mLstPriorityEntity.map{ it.description }
        mLstPriorityId = mLstPriorityEntity.map{ it.id }.toMutableList()
        //
        val adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                lstPriorities
        )
        //
        actTaskFormSpPriority.adapter = adapter
    }
    private fun openDatePickerDialog() {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day =calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
                this,
                this,
                year,
                month,
                day
        ).show()
    }

    private fun handleSave() {
        try{
            val description = actTaskFormEtDesc.text.toString()
            val priorityId = mLstPriorityId[actTaskFormSpPriority.selectedItemPosition]
            val complete = actTaskFormChkComplete.isChecked
            val duedate = actTaskFormBtnDate.text.toString()
            val userId = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID)
            //
            val taskEntity = TaskEntity(
                    0,
                    userId.toInt(),
                    priorityId,
                    description,
                    duedate,
                    complete
            )
            //
            mTaskBusiness.insert(taskEntity)
            //
        }catch (e: Exception){
            Toast.makeText(this,R.string.erro_generico,Toast.LENGTH_LONG).show()
        }
    }
}
