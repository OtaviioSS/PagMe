package com.pagme.app.ui.user

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pagme.app.R
import com.pagme.app.business.UserBusiness
import com.pagme.app.util.DatabaseRef
import kotlinx.android.synthetic.main.activity_my_profile.*

class Activity_My_Profile : AppCompatActivity() {
    private var userBusiness = UserBusiness()
    private val database = DatabaseRef().initializeDatabaseRefrence()
    private var auth: FirebaseAuth = Firebase.auth
    private val curUser = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        getUser()

        buttonSaveMyProfileView.setOnClickListener {
            userBusiness.updateUser(
                emailMyProfileView.text.toString(),
                userNameMyProfileView.text.toString()
            )
        }

        textDeleteMyProfileView.setOnClickListener { deleteAccount() }
    }


    private fun deleteAccount() {
        val builder = AlertDialog.Builder(this)
            .setMessage("Você tem certeza de que deseja excluir sua conta?")
            .setPositiveButton("Sim") { _, _ ->
                userBusiness.removeUser()
                startActivity(Intent(this, Activity_Login::class.java))
                finish()
            }
            .setNegativeButton("Não",
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.cancel()
                })
        builder.show()
    }

    private fun getUser() {
        val user = userBusiness.getUser()
        emailMyProfileView.setText(user.email)
        userNameMyProfileView.setText(user.userName)
    }
}