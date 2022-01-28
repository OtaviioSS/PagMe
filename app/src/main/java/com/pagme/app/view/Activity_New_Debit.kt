package com.pagme.app.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.pagme.app.R
import com.pagme.app.model.Card


class Activity_New_Debit : AppCompatActivity() {
    private val TAG = "ReadAndWriteSnippets"
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_debit)



        val cardListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val card = dataSnapshot.getValue<Card>()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadCard:onCancelled", databaseError.toException())
            }
        }
        database = Firebase.database.reference
        database.child("userOtavio").child("cards").addValueEventListener(cardListener)
        val languages = resources.getStringArray(R.array.Languages)

        val spinner = findViewById<Spinner>(R.id.spinnerCardNewDebitView)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item,languages)
            spinner.adapter = adapter}
        mudarTela()
    }


    fun mudarTela(){
        val btn = findViewById<Button>(R.id.btnOpenViewNewDebitView)
        btn.setOnClickListener(){
            val intent = Intent(this, Activity_AddCard::class.java)
            startActivity(intent)
        }
    }

}