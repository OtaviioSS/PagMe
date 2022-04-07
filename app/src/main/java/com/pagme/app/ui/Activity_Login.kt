package com.pagme.app.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pagme.app.R
import com.pagme.app.business.UserBusiness
import com.pagme.app.entity.User
import kotlinx.android.synthetic.main.activity_login.*

class Activity_Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var userBusiness = UserBusiness()

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
                    auth.signInWithEmailAndPassword(emailLoginView.text.toString(),passwordLoginView.text.toString()).addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            val intent= Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
                    }

                }
            }


        }
        buttonRegister.setOnClickListener {
            val intent = Intent(this, Activity_Register_User::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }


}