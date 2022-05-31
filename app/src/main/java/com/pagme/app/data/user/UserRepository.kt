package com.pagme.app.data.user

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.pagme.app.domain.model.User
import javax.inject.Inject


class UserRepository @Inject constructor(private val userDataSource: UserDataSource) {

    suspend fun createUser(email: String, password: String, userName: String): Task<AuthResult> =
        userDataSource.createUser(email, password, userName)

    suspend fun getUser(): User = userDataSource.getUser()

    suspend fun updateUser(email: String, name: String): Boolean =
        userDataSource.updateUser(email, name)

    suspend fun loginUser(email: String, password: String): Task<AuthResult> =
        userDataSource.loginUser(email, password)

    suspend fun singOut() = userDataSource.singOut()

    suspend fun deleteUser() = userDataSource.deleteUser()

}