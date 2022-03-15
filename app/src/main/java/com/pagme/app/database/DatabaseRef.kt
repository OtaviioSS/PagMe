package com.pagme.app.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DatabaseRef {

    fun initializeDatabaseRefrence(): DatabaseReference {
         val database: DatabaseReference = Firebase.database.reference
        return database
    }
}