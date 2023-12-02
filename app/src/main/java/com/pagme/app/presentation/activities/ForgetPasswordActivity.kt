package com.pagme.app.presentation.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pagme.app.databinding.ActivityForgetPasswordBinding


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
        val emailAddress = binding.emailForgetPasswordView.text.toString().trim()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (emailAddress.isEmpty()) {
            showSnackbar("Digite um email")
        } else if (!emailAddress.matches(emailPattern.toRegex())) {
            showSnackbar("Digite um email válido!")
        } else {
            val progressBar = binding.progressBar
            progressBar.visibility = View.VISIBLE

            Firebase.auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    Snackbar.make(binding.root, "Email enviado!", Snackbar.LENGTH_LONG).show()

                    if (task.isSuccessful) {
                        Log.d(TAG, "Email sent.")
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(this, LoginActivity::class.java))
                            binding.progressBar.visibility = View.GONE
                        }, 3000)
                    }
                }
        }
    }

    private fun showSnackbar(message: String) {
        Handler(Looper.getMainLooper()).postDelayed({
            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
            binding.progressBar.visibility = View.GONE
        }, 3000)
    }

}