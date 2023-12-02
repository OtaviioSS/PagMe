package com.pagme.app.presentation.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.pagme.app.MyApplication
import com.pagme.app.data.model.User
import com.pagme.app.databinding.ActivityFormUserBinding
import com.pagme.app.presentation.viewmodel.UserViewModel
import com.pagme.app.util.CreateUserResult

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
            val url = "https://incub.com.br/pagme-terms.html" // Substitua pelo URL correto
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        binding.textViewLinkTermoUso.setOnClickListener {
            val url = "https://incub.com.br/pagme-terms.html" // Substitua pelo URL correto
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }



        binding.buttonSaveNewUserView.setOnClickListener {
            with(binding) {
                progressBar.visibility = View.VISIBLE
                overlayView.visibility = View.VISIBLE

                if (emailNewUserView.text.toString().trim().isEmpty() ||
                    nameNewUserView.text.toString().trim().isEmpty() ||
                    passwordNewUserView.text.toString().trim().isEmpty()
                ) {
                    Snackbar.make(root, "Todos os campos são obrigatórios!", Snackbar.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                    overlayView.visibility = View.GONE
                } else if (checkBoxTermoUso.isChecked && checkBoxPolTicaPrivacidade.isChecked) {
                    val user = User(
                        email = emailNewUserView.text.toString().trim(),
                        userName = nameNewUserView.text.toString().trim(),
                        password = passwordNewUserView.text.toString().trim(),
                        termsUse = true,
                        privacyPolicy = true
                    )

                    userViewModel.createUserResult.observe(this@FormUserActivity, { result ->
                        when (result) {
                            is CreateUserResult.Success -> {
                                Snackbar.make(root, "Conta criada com sucesso!", Snackbar.LENGTH_LONG).show()
                                progressBar.visibility = View.GONE
                                overlayView.visibility = View.GONE
                                startActivity(Intent(this@FormUserActivity, VerifiedActivity::class.java))
                                finish()
                            }
                            is CreateUserResult.EmailExists -> {
                                Snackbar.make(root, "Email já cadastrado!", Snackbar.LENGTH_LONG).show()
                                progressBar.visibility = View.GONE
                                overlayView.visibility = View.GONE
                            }
                            is CreateUserResult.WeakPassword -> {
                                Snackbar.make(root, result.message, Snackbar.LENGTH_LONG).show()
                                progressBar.visibility = View.GONE
                                overlayView.visibility = View.GONE
                            }
                            is CreateUserResult.Failure -> {
                                Snackbar.make(root, "Não foi possível criar o usuário!", Snackbar.LENGTH_LONG).show()
                                progressBar.visibility = View.GONE
                                overlayView.visibility = View.GONE
                            }

                            else -> {      Snackbar.make(root, "Erro desconhecido!", Snackbar.LENGTH_LONG).show()
                                progressBar.visibility = View.GONE
                                overlayView.visibility = View.GONE}
                        }
                    })

                    userViewModel.create(user)
                } else {
                    Snackbar.make(root, "Aceite nossos termos de uso e política de privacidade!", Snackbar.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                    overlayView.visibility = View.GONE
                }
            }
        }
    }


}