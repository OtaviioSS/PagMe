package com.pagme.app.domain.usecase.user

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface CreateUserUseCase {

    suspend operator fun invoke(email: String, password: String,name:String): Task<AuthResult>

}