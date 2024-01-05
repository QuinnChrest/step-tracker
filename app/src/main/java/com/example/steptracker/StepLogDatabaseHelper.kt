package com.example.steptracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class StepLogDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "steptracker.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "steplogs"
        private const val COLUMN_ID = "id"
        private const val COLUMN_STEPS = "steps"
        private const val COLUMN_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_STEPS INTEGER, $COLUMN_DATE TEXT)"
        db?.execSQL(createTableQuery);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertStepLog(stepLog: StepLog){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_STEPS, stepLog.steps)
            put(COLUMN_DATE, stepLog.date)
        }
        db.insert(TABLE_NAME, null, values)
    }

    fun getStepLog(): List<StepLog>{
        val stepLog = mutableListOf<StepLog>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME ORDER BY DATE($COLUMN_DATE) DESC"
        val cursor = db.rawQuery(query, null)

        while(cursor.moveToNext()){
            stepLog.add(
                StepLog(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STEPS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                )
            )
        }

        cursor.close()
        db.close()

        Log.i("DATABASE", stepLog.toString())

        return stepLog
    }

    fun resetDatabase(db: SQLiteDatabase?) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }
}