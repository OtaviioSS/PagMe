package com.pagme.app.util

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DatabaseRef() {

    fun initializeDatabaseRefrence(): DatabaseReference {
        return Firebase.database.reference
    }
}