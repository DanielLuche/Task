package com.dluche.task.business

import android.content.Context
import com.dluche.task.R
import com.dluche.task.constants.TaskConstants
import com.dluche.task.entities.UserEntity
import com.dluche.task.repository.UserRepository
import com.dluche.task.util.SecurityPreferences
import com.dluche.task.util.ValidationException

//sem a palavra val, o parametro passado só é usado no context
class UserBusiness(val context: Context) {
    private val mUserRepository: UserRepository = UserRepository.getInstance(context)
    private val mSecurityPreferences = SecurityPreferences(context)

    fun insert(name: String, email: String, password: String) {
        //
        try {
            if (name == "" || email == "" || password == "") {
                throw ValidationException(context.getString(R.string.informe_todos_os_campos))
            }
            //
            if(mUserRepository.isEmailExistent(email)){
                throw ValidationException(context.getString(R.string.email_em_uso))
            }
            //
            val userId = mUserRepository.insert(name, email, password)
            //Salva dados od usr nas preferencias
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID,userId.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME,name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL,email)

        }catch (e: Exception){
            throw e
        }
    }
    //
    fun login(email:String , password: String): Boolean{
        val user: UserEntity? = mUserRepository.get(email, password)
        //
        return if(user != null){
            //Salva dados od usr nas preferencias
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID,user.id.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME,user.name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL,user.email)
            true
        }else{
            false
        }
    }

}