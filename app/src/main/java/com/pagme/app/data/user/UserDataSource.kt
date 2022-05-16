package com.pagme.app.data.user

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pagme.app.domain.model.User

interface UserDataSource {

    suspend fun createUser(email: String, password: String, userName: String): Task<AuthResult>

    suspend fun getUser(): User

    suspend fun updateUser(email: String, name: String): Boolean

    suspend fun loginUser(email: String, password: String, context: Context): Task<AuthResult>

    suspend fun singOut()

    suspend fun deleteUser()
}