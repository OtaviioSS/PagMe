package com.pagme.app.business

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.pagme.app.entity.User
import com.pagme.app.repository.UserRepository

class UserBusiness {
    private val userRepository = UserRepository()

    fun createNewUser(user: User,auth: FirebaseAuth): Task<AuthResult> {
        return userRepository.createUser(user,auth)
    }

    fun logInUser(user: User, auth: FirebaseAuth,context: Context): Task<AuthResult> {

        return userRepository.loginUser(user.email.toString(), user.passwordUser.toString(), auth,context)
    }

    fun getUser(userId:String): User {
        return userRepository.getUser(userId)
    }

    fun logOutUser(){
        userRepository.singOut()
    }
}