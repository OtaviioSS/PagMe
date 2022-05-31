package com.pagme.app.domain.usecase.user

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pagme.app.data.user.UserRepository
import javax.inject.Inject

class CreateUserUseCaseImplementation @Inject constructor(
    private val userRepository: UserRepository
): CreateUserUseCase {
    override suspend fun invoke(email: String, password: String,name: String): Task<AuthResult> {
        return userRepository.createUser(email,password,name)
    }
}