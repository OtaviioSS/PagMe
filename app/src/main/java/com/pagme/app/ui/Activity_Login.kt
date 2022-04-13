package com.pagme.app.ui

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pagme.app.R
import kotlinx.android.synthetic.main.activity_login.*

class Activity_Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        buttonLogin.setOnClickListener {
            when {
                emailLoginView.text.toString().isEmpty() -> {
                    Toast.makeText(this, "Por favor insira um email valido", Toast.LENGTH_LONG)
                        .show()

                }
                passwordLoginView.text.toString().isEmpty() -> {
                    Toast.makeText(this, "Por favor insira uma senha!", Toast.LENGTH_LONG).show()

                }
                else -> {
                    auth.signInWithEmailAndPassword(
                        emailLoginView.text.toString(),
                        passwordLoginView.text.toString()
                    ).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(
                            applicationContext,
                            exception.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
            }


        }
        buttonRegister.setOnClickListener {
            val intent = Intent(this, Activity_Register_User::class.java)
            startActivity(intent)
        }

        textResetPassword.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    Activity_Reset_Password::class.java
                )
            )
        }
    }


}