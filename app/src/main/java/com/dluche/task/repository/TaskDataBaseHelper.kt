package com.dluche.task.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dluche.task.constants.DataBaseConstants

class TaskDataBaseHelper(context:Context): SQLiteOpenHelper(context,DATABASE_NAME,null, DATABASE_VERSION) {
    //
    companion object {
        private val DATABASE_VERSION : Int = 1
        private val DATABASE_NAME : String = "task.db"
    }
     //SQLite
    //Tipos de dados do SQLite
    //INTEGER, REAL(DOUBLE), TEXT , BLOB

    private val createTableUser = """
        create table if not exists ${DataBaseConstants.USER.TABLE_NAME}(
         ${DataBaseConstants.USER.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
         ${DataBaseConstants.USER.COLUMNS.NAME} TEXT ,
         ${DataBaseConstants.USER.COLUMNS.EMAIL} TEXT ,
         ${DataBaseConstants.USER.COLUMNS.PASSWORD} TEXT
        );"""

    private val deleteTableUser = "drop table if exists ${DataBaseConstants.USER.TABLE_NAME}"

    override fun onCreate(sqLite: SQLiteDatabase) {

        sqLite.execSQL(createTableUser)
    }

    override fun onUpgrade(sqLite: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        sqLite.execSQL(createTableUser)
        //
        sqLite.execSQL(deleteTableUser)
    }
}