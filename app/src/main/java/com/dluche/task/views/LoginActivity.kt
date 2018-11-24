package com.dluche.task.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.dluche.task.R
import com.dluche.task.business.UserBusiness
import com.dluche.task.constants.TaskConstants
import com.dluche.task.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness : UserBusiness
    private lateinit var mSecurityPreferences : SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //instancia var
        mUserBusiness = UserBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)
        //
        setListeners()
        //
        verifyLogedUser()
    }

    private fun verifyLogedUser() {
        val id = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID)
        val name = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME)

        if(id != "" && name != "" ){
            callMainAct()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.actLoginBtnLogin -> {
                handleLogin()
            }
        }
    }

    private fun setListeners() {
        actLoginBtnLogin.setOnClickListener(this)
    }


    private fun handleLogin() {
        val email = actLoginEtEmail.text.toString()
        val password = actLoginEtPwd.text.toString()
        //
        if(mUserBusiness.login(email, password)){
            callMainAct()
        }else{
            Toast.makeText(this,getString(R.string.login_error_msg),Toast.LENGTH_SHORT).show()
        }
    }

    private fun callMainAct(){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}
