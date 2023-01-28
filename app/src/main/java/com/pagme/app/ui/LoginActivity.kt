package com.pagme.app.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.lifecycle.lifecycleScope
import com.pagme.app.R
import com.pagme.app.data.AppDatabase
import com.pagme.app.databinding.ActivityListDebtBinding
import com.pagme.app.databinding.ActivityLoginBinding
import com.pagme.app.preferences.dataStore
import com.pagme.app.preferences.userLoggedPreferences
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val dao by lazy {
        AppDatabase.instance(this).userDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        listeners()

    }

    private fun listeners() {
        binding.buttonSaveLoginView.setOnClickListener {
            val email = binding.emailLoginView.text.toString()
            val password = binding.passwordLoginView.text.toString()
            authenticate(email, password)
        }

        binding.textRegisterLoginView.setOnClickListener {
            startActivity(Intent(this,FormUserActivity::class.java))
        }
    }

    private fun authenticate(email: String, password: String) {
        lifecycleScope.launch {
            dao.authenticateUser(email, password)?.let { user ->
                dataStore.edit { preferences ->
                    preferences[userLoggedPreferences]= user.userId

                }
                startActivity(Intent(this@LoginActivity,ListDebtActivity::class.java))

            }?: Toast.makeText(this@LoginActivity,"Erro ao autenticar",Toast.LENGTH_LONG).show()
        }
    }
}