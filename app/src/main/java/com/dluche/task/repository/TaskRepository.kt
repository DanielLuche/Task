package com.dluche.task.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.dluche.task.constants.DataBaseConstants
import com.dluche.task.entities.TaskEntity

class TaskRepository private constructor(context: Context) {

    private var mTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    companion object {
        private var INSTANCE: TaskRepository? = null
        //
        fun getInstance(context: Context): TaskRepository {
            if (INSTANCE == null) {
                INSTANCE = TaskRepository(context)
            }
            //
            return INSTANCE as TaskRepository
        }
    }

    //
    fun get(id: Int): TaskEntity? {
        var mTaskEntity: TaskEntity? = null

        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase
            //Array com o nome das colunas a serem retornadas
            val projection = arrayOf(
                    DataBaseConstants.TASK.COLUMNS.ID,
                    DataBaseConstants.TASK.COLUMNS.USER_ID,
                    DataBaseConstants.TASK.COLUMNS.PRIORITY_ID,
                    DataBaseConstants.TASK.COLUMNS.DESCRIPTION,
                    DataBaseConstants.TASK.COLUMNS.DUEDATE,
                    DataBaseConstants.TASK.COLUMNS.COMPLETE

            )
            //
            val selection = "${DataBaseConstants.TASK.COLUMNS.ID} = ?"
            val selectionArgs = arrayOf(id.toString())
            //Query usando o metodo query
            cursor = db.query(DataBaseConstants.TASK.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
            if (cursor.count > 0) {
                cursor.moveToFirst()
                val taskId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.ID))
                val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.USER_ID))
                val priorityId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.PRIORITY_ID))
                val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DESCRIPTION))
                val duedate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DUEDATE))
                val complete = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.COMPLETE)) == 1)
                //
                mTaskEntity =
                        TaskEntity(
                                taskId,
                                userId,
                                priorityId,
                                description,
                                duedate,
                                complete
                        )
            }
            cursor.close()
        } catch (e: Exception) {
            return mTaskEntity
        }
        return mTaskEntity
    }

    //
    fun getList(userId: Int): MutableList<TaskEntity> {
        val list = mutableListOf<TaskEntity>()
        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase
            //
            cursor = db.rawQuery(
                    """ SELECT
                            *
                        FROM ${DataBaseConstants.TASK.TABLE_NAME}
                        WHERE
                           ${DataBaseConstants.TASK.COLUMNS.USER_ID} = $userId
                    """.trimMargin()
                    , null)
            //
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.ID))
                    val priorityId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.PRIORITY_ID))
                    val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DESCRIPTION))
                    val duedate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DUEDATE))
                    val complete = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.COMPLETE)) == 1)


                    //
                    list.add(
                            TaskEntity(
                                    id,
                                    userId,
                                    priorityId,
                                    description,
                                    duedate,
                                    complete
                            )
                    )
                }
            }
            //
            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
            return list
        }

        return list
    }
    //
    fun insert(task: TaskEntity) {
        try {
            //select , update,insert , delete
            val db = mTaskDataBaseHelper.writableDatabase
            //Aqui, talvez fosse interessante criar um retorno customizado do metodo get{} da propriedade
            val complete: Int = if (task.complete) 1 else 0
            //
            val insertValues = ContentValues()
            //
            insertValues.put(DataBaseConstants.TASK.COLUMNS.USER_ID, task.userId)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.PRIORITY_ID, task.priorityId)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.DESCRIPTION, task.description)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.DUEDATE, task.dueDate)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.COMPLETE, complete)
            //
            db.insert(DataBaseConstants.TASK.TABLE_NAME, null, insertValues)
        } catch (e: java.lang.Exception) {
            throw e
        }
    }

    //
    fun update(task: TaskEntity) {
        try {
            //select , update,insert , delete
            val db = mTaskDataBaseHelper.writableDatabase
            //Aqui, talvez fosse interessante criar um retorno customizado do metodo get{} da propriedade
            val complete: Int = if (task.complete) 1 else 0
            //
            val updateValues = ContentValues()
            //
            //updateValues.put(DataBaseConstants.TASK.COLUMNS.ID, task.id)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.USER_ID, task.userId)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.PRIORITY_ID, task.priorityId)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.DESCRIPTION, task.description)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.DUEDATE, task.dueDate)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.COMPLETE, complete)
            //
            val whereClause = "${DataBaseConstants.TASK.COLUMNS.ID} = ?"
            val whereArgs = arrayOf(task.id.toString())
            db.update(DataBaseConstants.TASK.TABLE_NAME, updateValues, whereClause, whereArgs)
        } catch (e: java.lang.Exception) {
            throw e
        }

    }

    //
    fun delete(id: Int) {
        try {
            //select , update,insert , delete
            val db = mTaskDataBaseHelper.writableDatabase
            val whereClause = "${DataBaseConstants.TASK.COLUMNS.ID} = ?"
            val whereArgs = arrayOf(id.toString())
            db.delete(DataBaseConstants.TASK.TABLE_NAME, whereClause, whereArgs)
        } catch (e: java.lang.Exception) {
            throw e
        }
    }
}