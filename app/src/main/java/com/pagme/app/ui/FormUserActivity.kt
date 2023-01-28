package com.pagme.app.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.pagme.app.data.AppDatabase
import com.pagme.app.databinding.ActivityFormUserBinding
import com.pagme.app.domain.model.User
import com.pagme.app.repository.UserRepository
import kotlinx.coroutines.launch
import java.util.*

class FormUserActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormUserBinding.inflate(layoutInflater)
    }

    private val repository by lazy {
        UserRepository(
            AppDatabase.instance(this).userDao()
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        listeners()

    }

    private fun listeners() {
        binding.buttonSaveNewUserView.setOnClickListener {
                        lifecycleScope.launch {
                            val newUser = createUser()
                            repository.save(newUser)
                            Toast.makeText(this@FormUserActivity, "Usuario criado", Toast.LENGTH_LONG)
                                .show()
                            finish()
                        }}
    }

    private fun createUser(): User {
        return User(
                userId = UUID.randomUUID().toString(),
                email = binding.emailNewUserView.text.toString(),
                userName = binding.nameNewUserView.text.toString(),
                userPassword = binding.passwordNewUserView.text.toString()
            )



    }



}