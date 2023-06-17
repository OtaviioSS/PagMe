package com.pagme.app

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pagme.app.databinding.ActivityForgetPasswordBinding
import com.pagme.app.ui.LoginActivity
import com.pagme.app.ui.UserBaseActivity


class ForgetPasswordActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityForgetPasswordBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonEnviarForgetPasswordView.setOnClickListener {
            sendEmail()
        }

    }

    private fun sendEmail() {
        val emailAddress = binding.emailForgetPasswordView.text.toString()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE
        if (emailAddress.isEmpty()) {
            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(this, "Digite um email", Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE
            }, 3000)
        } else if (!emailAddress.matches(emailPattern.toRegex())) {
            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(this, "Digite um email valido!", Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE
            }, 3000)

        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                Firebase.auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Email sent.")
                            Toast.makeText(this, "Email enviado", Toast.LENGTH_LONG).show()
                        }
                    }
                progressBar.visibility = View.GONE
                startActivity(Intent(this, LoginActivity::class.java))
            }, 3000)

        }

    }
}