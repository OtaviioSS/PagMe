package com.pagme.app.presentation.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.pagme.app.MyApplication
import com.pagme.app.data.model.User
import com.pagme.app.databinding.ActivityProfileUserBinding
import com.pagme.app.presentation.viewmodel.UserViewModel

class ProfileUserActivity : UserBaseActivity() {


    private lateinit var userViewModel: UserViewModel


    private val binding by lazy {
        ActivityProfileUserBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val appComponent = (application as MyApplication).appComponent
        appComponent.inject(this)
        val userViewModelFactory = appComponent.provideUserViewModelFactory()
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
        listeners()

        userViewModel.getById().observe(this) { user ->
            if (user != null) {
                binding.emailProfileUserView.setText(user.email)
                binding.nameProfileUserView.setText(user.userName.toString())
            }

        }

        if (!userViewModel.userVerified()) {
            binding.emailVerifiedMessage.visibility = View.VISIBLE
        }
    }

    private fun listeners() {
        binding.buttonSaveProfileUserView.setOnClickListener {
            val user = User(
                email = binding.emailProfileUserView.text.toString().trim(),
                userName = binding.nameProfileUserView.text.toString().trim()
            )
            userViewModel.update(user) { success ->
                runOnUiThread {
                    if (!success) {
                        Toast.makeText(
                            this,
                            "Não foi possivel atualizar os dados do usuario",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {


                        Snackbar.make(binding.root,"Dados de perfil atualizado!", Snackbar.LENGTH_LONG).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 3000)

                    }

                }

            }
        }


        binding.textViewDeleteUser.setOnClickListener {
            showConfirmDeleteDialog()
        }
    }

    private fun showConfirmDeleteDialog() {
        AlertDialog.Builder(this)
            .setMessage("Tem certeza que deseja excluir a contas? Não será possivel recupera-lá e todos os seus dados serão removidos")
            .setTitle("Excluir Conta")
            .setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, which ->
                userViewModel.delete { success ->
                    Toast.makeText(this@ProfileUserActivity, "Conta removida", Toast.LENGTH_LONG)
                        .show()
                    startActivity(
                        Intent(
                            this,
                            LoginActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    )
                    finish()
                }

            })
            .setNegativeButton("Não", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
            .create()
            .show()
    }


}