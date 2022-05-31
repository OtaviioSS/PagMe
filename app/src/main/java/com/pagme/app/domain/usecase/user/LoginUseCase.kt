package com.pagme.app.domain.usecase.user

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pagme.app.domain.model.User


interface LoginUseCase {
    suspend operator fun invoke(email: String, password: String): Task<AuthResult>

}