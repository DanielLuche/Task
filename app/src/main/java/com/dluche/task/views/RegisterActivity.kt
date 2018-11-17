package com.dluche.task.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.dluche.task.R
import com.dluche.task.business.UserBusiness
import com.dluche.task.util.ValidationException
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness : UserBusiness


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //Eventos
        setListners()
        //Instanciar VAr
        mUserBusiness = UserBusiness(this)
        //
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.actRegisterBtnRegister -> {
                handleSave()
            }
        }
    }

    private fun setListners(){
        actRegisterBtnRegister.setOnClickListener(this)
    }

    private fun handleSave(){
        try{
            val name =  actRegisterEtName.text.trim().toString()
            val email = actRegisterEtEmail.text.trim().toString()
            val password = actRegisterEtPwd.text.trim().toString()
            //faz o insert
            mUserBusiness.insert(name,email,password)
        }catch (e: ValidationException){
            Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
        } catch (e: Exception){
            Toast.makeText(this,getString(R.string.erro_generico),Toast.LENGTH_LONG).show()
        }

    }


}
