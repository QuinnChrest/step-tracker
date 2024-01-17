package com.example.steptracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.sql.PreparedStatement
import java.sql.ResultSet

class FitnessDiaryDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "fitness-diary.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "diary"
        private const val COLUMN_ID = "id"
        private const val COLUMN_STEPS = "steps"
        private const val COLUMN_CALORIES = "calories"
        private const val COLUMN_WEIGHT = "weight"
        private const val COLUMN_FATPERCENTAGE = "fatPercentage"
        private const val COLUMN_NAVELINCHES = "navelInches"
        private const val COLUMN_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_STEPS INTEGER, $COLUMN_CALORIES REAL, $COLUMN_WEIGHT REAL, $COLUMN_FATPERCENTAGE REAL, $COLUMN_NAVELINCHES REAL, $COLUMN_DATE TEXT)"
        db?.execSQL(createTableQuery);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertDiaryEntry(diaryEntry: DiaryEntry){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_STEPS, diaryEntry.steps)
            put(COLUMN_CALORIES, diaryEntry.calories)
            put(COLUMN_WEIGHT, diaryEntry.weight)
            put(COLUMN_FATPERCENTAGE, diaryEntry.fatPercentage)
            put(COLUMN_NAVELINCHES, diaryEntry.navelInches)
            put(COLUMN_DATE, diaryEntry.date)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun checkIfDateExists(date: String): Boolean{
        val db = readableDatabase
        val checkForDuplicateQuery = "SELECT COUNT(*) FROM $TABLE_NAME WHERE $COLUMN_DATE = $date"
        val cursor = db.rawQuery(checkForDuplicateQuery, null)
        var exists = false

        if (cursor.moveToFirst()) {
            exists = cursor.getInt(0) > 0
        }

        cursor.close()
        db.close()
        return exists
    }

    fun getStepLog(): List<DiaryEntry>{
        val diary = mutableListOf<DiaryEntry>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_DATE DESC"
        val cursor = db.rawQuery(query, null)

        while(cursor.moveToNext()){
            diary.add(
                DiaryEntry(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STEPS)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_CALORIES)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_WEIGHT)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_FATPERCENTAGE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_NAVELINCHES)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                )
            )
        }

        cursor.close()
        db.close()

        Log.i("DATABASE", diary.toString())

        return diary
    }

    fun resetDatabase(db: SQLiteDatabase?) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }
}