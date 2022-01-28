package com.pagme.app.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.pagme.app.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mudarTela()
    }

    fun mudarTela(){
        val btn = findViewById<Button>(R.id.btnMudar)
        btn.setOnClickListener(){
            val intent = Intent(this, Activity_New_Debit::class.java)
            startActivity(intent)
        }
    }
}