package com.pagme.app.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pagme.app.data.model.User
import com.pagme.app.extensions.goTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class UserBaseActivity : AppCompatActivity() {



    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user = Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            checkLoggedUser()

        }
    }


    private fun goToLogin() {
        goTo(LoginActivity::class.java) {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        finish()
    }

    private fun checkLoggedUser() {
        if (Firebase.auth.currentUser  == null){
            goToLogin()
        }else{
            Log.i("checkLoggerdUser:", "Usuario não é null "+ Firebase.auth.currentUser!!.uid.toString() )
        }
    }

    protected fun logoutUser() {
        Firebase.auth.signOut()
        goToLogin()
    }
}