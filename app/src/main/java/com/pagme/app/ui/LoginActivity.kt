package com.pagme.app.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pagme.app.ForgetPasswordActivity
import com.pagme.app.databinding.ActivityLoginBinding
import java.util.*


class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }



    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        listeners()
        auth = Firebase.auth

    }

    private fun listeners() {
        binding.buttonSaveLoginView.setOnClickListener {
            authenticate(binding.emailLoginView.text.toString(), binding.passwordLoginView.text.toString())
        }

        binding.textRegisterLoginView.setOnClickListener {
            startActivity(Intent(this, FormUserActivity::class.java))
        }

        binding.textViewForgetPasswordLoginView.setOnClickListener {
            startActivity(Intent(this,ForgetPasswordActivity::class.java))
        }
    }

    private fun authenticate(email: String, password: String) {
        try{
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@LoginActivity) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        Log.i("authenticate:", "success" )
                        startActivity(Intent(this,ListDebtActivity::class.java))
                        finish()

                    } else {
                        Log.i("authenticate:", "error "+ Firebase.auth.currentUser!!.uid.toString() )
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }catch (e:Exception){
            Toast.makeText(this,"Erro ao tentar logar",Toast.LENGTH_LONG).show()
        }


    }




}