package com.pagme.app.domain.usecase.user

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pagme.app.data.user.UserRepository
import com.pagme.app.domain.model.User
import javax.inject.Inject

class LoginUseCaseImplementation @Inject constructor(
    private val userRepository: UserRepository
): LoginUseCase{
    override suspend fun invoke(email: String, password: String): Task<AuthResult> {
        return userRepository.loginUser(email,password)
    }


}