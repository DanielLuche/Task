package com.dluche.task.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.dluche.task.constants.DataBaseConstants

//Class Singleton
class UserRepository private constructor(context: Context) {
    private var mTaskDataBaseHelper :TaskDataBaseHelper = TaskDataBaseHelper(context)

    companion object {
        private var INSTANCE: UserRepository? = null
        //
        fun getInstance(context: Context): UserRepository {
            if(INSTANCE == null){
                INSTANCE = UserRepository(context)
            }
            //
            return INSTANCE as UserRepository
        }
    }
    //
    fun insert(name: String, email: String, password: String): Int{
        //select , update,insert , delete
        val db = mTaskDataBaseHelper.writableDatabase
        //
        val insertValues = ContentValues()
        //
        insertValues.put(DataBaseConstants.USER.COLUMNS.NAME,name)
        insertValues.put(DataBaseConstants.USER.COLUMNS.EMAIL,email)
        insertValues.put(DataBaseConstants.USER.COLUMNS.PASSWORD,password)
        //
        return db.insert(DataBaseConstants.USER.TABLE_NAME,null,insertValues).toInt()
    }

    fun isEmailExistent(email: String): Boolean{
        var ret: Boolean = false
        try {
            val db = mTaskDataBaseHelper.readableDatabase
            val cursor: Cursor
            //Array com o nome das colunas a serem retornadas
            val projection = arrayOf(DataBaseConstants.USER.COLUMNS.ID)
            //
            val selection = "${DataBaseConstants.USER.COLUMNS.EMAIL} = ?"
            val selectionArgs = arrayOf(email)
            //Query usando o metodo query
            cursor = db.query(DataBaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null, null,null)
            //Query crua
            //db.rawQuery("SELECT * FROM USER WHERE EMAIL = $email")
            ret = cursor.count > 0
            cursor.close()
        }catch (e: Exception){
            throw  e
        }
        return ret
    }

}