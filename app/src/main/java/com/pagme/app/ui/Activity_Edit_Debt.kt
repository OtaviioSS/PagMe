package com.pagme.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pagme.app.R
import kotlinx.android.synthetic.main.activity_edit_debt.*

class Activity_Edit_Debt : AppCompatActivity() {
    lateinit var idDebt:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_debt)

        val extras = intent.extras
        if (extras != null) {
            idDebt = extras.getString("idDebt").toString()
            nameBuyerEditDebt.setText(idDebt)

        }
    }
}