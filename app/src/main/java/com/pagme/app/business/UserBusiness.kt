package com.pagme.app.business

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.pagme.app.entity.User
import com.pagme.app.repository.UserRepository

class UserBusiness {
    private val userRepository = UserRepository()

    fun createNewUser(email:String,password:String,userName:String): Task<AuthResult> {
        return userRepository.createUser(email.trim(),password,userName)
    }

    fun logInUser(email: String,password: String, context: Context): Task<AuthResult> {

        return userRepository.loginUser(email,password, context)
    }

    fun getUser(): User {
        return userRepository.getUser()
    }

    fun updateUser(email: String,name:String): Boolean {
        return userRepository.updateUser(email,name)
    }

    fun logOutUser(){
        userRepository.singOut()
    }

    fun removeUser(){
        userRepository.deleteUser()
    }
}