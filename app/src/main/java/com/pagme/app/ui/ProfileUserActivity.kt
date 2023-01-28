package com.pagme.app.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.pagme.app.data.AppDatabase
import com.pagme.app.databinding.ActivityProfileUserBinding
import com.pagme.app.domain.model.User
import com.pagme.app.repository.UserRepository
import com.pagme.app.util.USER_KEY_ID
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class ProfileUserActivity : UserBaseActivity() {

    private lateinit var userLoagado: User

    private val binding by lazy {
        ActivityProfileUserBinding.inflate(layoutInflater)
    }

    private val repository by lazy {
        UserRepository(AppDatabase.instance(this).userDao())
    }

    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        listeners()
        tryLoadingDebt()
        lifecycleScope.launch {
            launch {
                tryGetUser()
            }
        }

    }

    private fun listeners() {
        binding.buttonSaveProfileUserView.setOnClickListener {
            lifecycleScope.launch {
                repository.update(updateUser(userId))
                Toast.makeText(
                    this@ProfileUserActivity,
                    "Dados Atualizados",
                    Toast.LENGTH_LONG
                )
                    .show()
                finish()

            }
        }
        binding.textViewAlterPassword.setOnClickListener {
            binding.textFieldPasswordProfileUserView.visibility = View.VISIBLE
            binding.passwordProfileUserView.setText("")
            it.visibility = View.GONE
        }

        binding.textViewDeleteUser.setOnClickListener {
            showConfirmDeleteDialog()
        }
    }

    private fun showConfirmDeleteDialog() {
        AlertDialog.Builder(this)
            .setMessage("Tem certeza que deseja exluir a contas? Não será possivel recupera-lá")
            .setTitle("Excluir Conta" + userLoagado.userName)
            .setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, which ->
                lifecycleScope.launch {
                    repository.delete(userLoagado)

                }
                Toast.makeText(
                    this@ProfileUserActivity,
                    "Dados Atualizados",
                    Toast.LENGTH_LONG
                ).show()
                startActivity(Intent(this,LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            })
            .create()
            .show()
    }

    private fun updateUser(userId: String): User {
        return userId.let { id ->
            User(
                userId = userId,
                email = binding.emailProfileUserView.text.toString(),
                userName = binding.nameProfileUserView.text.toString(),
                userPassword = binding.passwordProfileUserView.text.toString()
            )
        }


    }


    private fun tryLoadingDebt() {
        userId = intent.getStringExtra(USER_KEY_ID).toString()
    }


    private suspend fun tryGetUser() {
        userId.let { id ->
            repository.getToId(id)
                .filterNotNull()
                .collect { userFound ->
                    userLoagado = userFound
                    userId = userFound.userId
                    binding.emailProfileUserView.setText(userFound.email)
                    binding.nameProfileUserView.setText(userFound.userName)
                    binding.passwordProfileUserView.setText(userFound.userPassword)

                }

        }

    }
}