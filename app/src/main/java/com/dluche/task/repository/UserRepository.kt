package com.dluche.task.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.dluche.task.constants.DataBaseConstants
import com.dluche.task.entities.UserEntity

//Class Singleton
class UserRepository private constructor(context: Context) {
    private var mTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    companion object {
        private var INSTANCE: UserRepository? = null
        //
        fun getInstance(context: Context): UserRepository {
            if (INSTANCE == null) {
                INSTANCE = UserRepository(context)
            }
            //
            return INSTANCE as UserRepository
        }
    }

    fun get(email: String, password: String): UserEntity? {
        var mUserEntity : UserEntity? = null

        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase
            //Array com o nome das colunas a serem retornadas
            val projection = arrayOf(
                    DataBaseConstants.USER.COLUMNS.ID,
                    DataBaseConstants.USER.COLUMNS.NAME,
                    DataBaseConstants.USER.COLUMNS.EMAIL,
                    DataBaseConstants.USER.COLUMNS.PASSWORD
            )
            //
            val selection = "${DataBaseConstants.USER.COLUMNS.EMAIL} = ? AND ${DataBaseConstants.USER.COLUMNS.PASSWORD} = ?"
            val selectionArgs = arrayOf(email, password)
            //Query usando o metodo query
            cursor = db.query(DataBaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
            if(cursor.count > 0){
                cursor.moveToFirst()
                val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.ID))
                val userName = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.NAME))
                val userEmail = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.EMAIL))
                //
                mUserEntity = UserEntity(userId,userName,userEmail)
            }
            cursor.close()
        } catch (e: Exception) {
            return mUserEntity
        }
        return mUserEntity
    }

    //
    fun insert(name: String, email: String, password: String): Int {
        //select , update,insert , delete
        val db = mTaskDataBaseHelper.writableDatabase
        //
        val insertValues = ContentValues()
        //
        insertValues.put(DataBaseConstants.USER.COLUMNS.NAME, name)
        insertValues.put(DataBaseConstants.USER.COLUMNS.EMAIL, email)
        insertValues.put(DataBaseConstants.USER.COLUMNS.PASSWORD, password)
        //
        return db.insert(DataBaseConstants.USER.TABLE_NAME, null, insertValues).toInt()
    }

    fun isEmailExistent(email: String): Boolean {
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
            cursor = db.query(DataBaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
            //Query crua
            //db.rawQuery("SELECT * FROM USER WHERE EMAIL = $email")
            ret = cursor.count > 0
            cursor.close()
        } catch (e: Exception) {
            throw  e
        }
        return ret
    }

}