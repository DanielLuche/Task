package com.dluche.task.constants

class DataBaseConstants {

    object USER{
        val TABLE_NAME = "USER"
        object COLUMNS{
            val ID = "ID"
            val NAME = "NAME"
            val EMAIL = "EMAIL"
            val PASSWORD = "PASSWORD"
        }
    }

    object PRIORITY{
        val TABLE_NAME = "PRIORITY"
        object COLUMNS{
            val ID = "ID"
            val DESCRIPTION = "DESCRIPTION"
        }
    }

    object TASK{
        val TABLE_NAME = "TASK"
        object COLUMNS{
            val ID = "ID"
            val USER_ID = "USER_ID"
            val PRIORITY_ID = "PRIORITY_ID"
            val DESCRIPTION = "DESCRIPTION"
            val COMPLETE = "COMPLETE"
            val DUEDATE = "DUEDATE"
        }
    }
}