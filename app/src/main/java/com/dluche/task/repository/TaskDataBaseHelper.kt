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

    private val createTablePriority = """
        create table if not exists ${DataBaseConstants.PRIORITY.TABLE_NAME}(
         ${DataBaseConstants.PRIORITY.COLUMNS.ID} INTEGER PRIMARY KEY,
         ${DataBaseConstants.PRIORITY.COLUMNS.DESCRIPTION} TEXT
        );"""

    private val createTableTask = """
        create table if not exists ${DataBaseConstants.TASK.TABLE_NAME}(
         ${DataBaseConstants.TASK.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
         ${DataBaseConstants.TASK.COLUMNS.USER_ID} INTEGER ,
         ${DataBaseConstants.TASK.COLUMNS.PRIORITY_ID} INTEGER ,
         ${DataBaseConstants.TASK.COLUMNS.DESCRIPTION} TEXT ,
         ${DataBaseConstants.TASK.COLUMNS.COMPLETE} INTEGER ,
         ${DataBaseConstants.TASK.COLUMNS.DUEDATE} TEXT
        );"""

    private val deleteTableUser = "drop table if exists ${DataBaseConstants.USER.TABLE_NAME}"
    private val deleteTablePriority = "drop table if exists ${DataBaseConstants.PRIORITY.TABLE_NAME}"
    private val deleteTableTask = "drop table if exists ${DataBaseConstants.TASK.TABLE_NAME}"

    private val insertPriorities = """
        INSERT INTO ${DataBaseConstants.PRIORITY.TABLE_NAME}
         VALUES (1,'Baixa'),
                (2,'Media'),
                (3,'Alta'),
                (4,'Critica');

        """

    override fun onCreate(sqLite: SQLiteDatabase) {
        sqLite.execSQL(createTableUser)
        sqLite.execSQL(createTablePriority)
        sqLite.execSQL(createTableTask)
        sqLite.execSQL(insertPriorities)
    }

    override fun onUpgrade(sqLite: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //
        sqLite.execSQL(deleteTableUser)
        sqLite.execSQL(deleteTablePriority)
        sqLite.execSQL(deleteTableTask)
       //
        sqLite.execSQL(createTableUser)
        sqLite.execSQL(createTablePriority)
        sqLite.execSQL(createTableTask)

    }
}