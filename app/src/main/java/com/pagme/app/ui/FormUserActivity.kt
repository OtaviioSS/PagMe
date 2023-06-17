package com.pagme.app.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pagme.app.MyApplication
import com.pagme.app.databinding.ActivityFormUserBinding
import com.pagme.app.data.model.User
import com.pagme.app.presentation.viewmodel.UserViewModel

class FormUserActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    private val binding by lazy {
        ActivityFormUserBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val appComponent = (application as MyApplication).appComponent
        appComponent.inject(this)
        val userViewModelFactory = appComponent.provideUserViewModelFactory()
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
        listeners()

    }

    private fun listeners() {
        binding.textViewLinkPoliticasPrivacidade.setOnClickListener {
            val url = "https://www.incub.com.br" // Substitua pelo URL correto
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        binding.textViewLinkTermoUso.setOnClickListener {
            val url = "https://www.incub.com.br" // Substitua pelo URL correto
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        binding.buttonSaveNewUserView.setOnClickListener {
            val progressBar = binding.progressBar
            val overlayView = binding.overlayView
            val email = binding.emailNewUserView.text.toString().trim()
            val userName = binding.nameNewUserView.text.toString().trim()
            val password = binding.passwordNewUserView.text.toString().trim()
            val checkBoxTermoUso = binding.checkBoxTermoUso
            val checkBoxPolTicaPrivacidade = binding.checkBoxPolTicaPrivacidade
            binding.progressBar.visibility = View.VISIBLE
            overlayView.visibility = View.VISIBLE

            if (email.isEmpty() || userName.isEmpty() || password.isEmpty()) {
                Handler(Looper.getMainLooper()).postDelayed({
                    Toast.makeText(this, "Todos os campos são obrigatorios!", Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.GONE
                    overlayView.visibility = View.GONE
                }, 1500)


            }
            else if (checkBoxTermoUso.isChecked && checkBoxPolTicaPrivacidade.isChecked) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val user = User(
                        email = binding.emailNewUserView.text.toString().trim(),
                        userName = binding.nameNewUserView.text.toString().trim(),
                        password = binding.passwordNewUserView.text.toString().trim(),
                        termsUse = true,
                        privacyPolicy =  true

                    )
                    userViewModel.create(user) { success ->
                        runOnUiThread {
                            if (!success) {
                                Toast.makeText(
                                    this,
                                    "Não foi possivel criar o usuario",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                binding.progressBar.visibility = View.GONE
                                overlayView.visibility = View.GONE
                            } else {
                                Toast.makeText(this, "Conta criada com sucesso", Toast.LENGTH_SHORT)
                                    .show()
                                binding.progressBar.visibility = View.GONE
                                overlayView.visibility = View.GONE
                                finish()
                            }

                        }

                    }
                }, 3000)
            }
            else {
                Toast.makeText(this, "Aceite nossos termos de uso e politica de privacidade!", Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE
                overlayView.visibility = View.GONE
            }
        }
    }


}