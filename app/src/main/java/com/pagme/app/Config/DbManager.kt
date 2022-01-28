package com.pagme.app.Config

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DbManager {

    //database name
    var dbName = "Pagme"

    //table name
    var dbTableCard = "Card"

    //columns
    var colID = "ID"
    var colCardName = "cardName"
    var colClosingDate = "closingDate"
    var colDueDate = "dueDate"

    //database version
    var dbVersion = 1

    //CREATE TABLE IF NOT EXISTS MyNotes (ID INTEGER PRIMARY KEY, title TEXT, Description TEXT)
    val sqlCreateTable =
        "CREATE TABLE IF NOT EXISTS $dbTableCard ($colID INTEGER PRIMARY KEY,$colCardName TEXT, $colClosingDate TEXT, $colDueDate)"

    var sqlDB: SQLiteDatabase? = null

    constructor(context: Context)
    {
        var db = DatabaseHelperNotes(context)
        sqlDB = db.writableDatabase
    }

    inner class DatabaseHelperNotes : SQLiteOpenHelper {
        var context: Context? = null

        constructor(context: Context) : super(context, dbName, null, dbVersion) {
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            Toast.makeText(this.context, "database created...", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table if Exists$dbTableCard")
        }

    }


}
