package com.pagme.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pagme.app.R
import com.pagme.app.business.UserBusiness
import com.pagme.app.entity.User
import kotlinx.android.synthetic.main.activity_register_user.*

class Activity_Register_User : AppCompatActivity() {
    private val userBusiness = UserBusiness()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        buttonSaveNewUser.setOnClickListener {
            if (userNameCreateUserView.text.toString().isEmpty()||passwordCreateUserView.text.toString().isEmpty()||emailCreateUserView.text.toString().isEmpty()) {
                Toast.makeText(this, "Por favor preencha todos os campos", Toast.LENGTH_LONG).show()
            } else {
                userBusiness.createNewUser(
                    emailCreateUserView.text.toString(),
                    passwordCreateUserView.text.toString(),
                    userNameCreateUserView.text.toString()
                ).addOnSuccessListener {
                    Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_LONG).show()
                    finish()
                }.addOnFailureListener { exception ->
                    Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_LONG).show()

                }
            }
        }

    }


}