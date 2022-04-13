package com.pagme.app.ui

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pagme.app.R
import kotlinx.android.synthetic.main.activity_reset_password.*

class Activity_Reset_Password : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        buttonSend.setOnClickListener { senLinkResetPassword() }

    }

    private fun senLinkResetPassword() {
        Firebase.auth.sendPasswordResetEmail(emailResetPassword.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                    Toast.makeText(
                        this,
                        "Mensagem enviada para " + emailResetPassword.text.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                    emailResetPassword.setText("")
                }
            }
    }

}