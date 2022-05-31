package com.pagme.app.domain.usecase.user

import com.pagme.app.data.user.UserRepository
import com.pagme.app.domain.model.User
import javax.inject.Inject


class GetUserUseCaseImplementation @Inject constructor(
    private val userRepository: UserRepository
): GetUserUseCase {
    override suspend fun invoke(): User {
        return userRepository.getUser()
    }

}