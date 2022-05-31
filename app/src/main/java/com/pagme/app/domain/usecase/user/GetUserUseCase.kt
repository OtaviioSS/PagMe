package com.pagme.app.domain.usecase.user

import com.pagme.app.domain.model.User

interface GetUserUseCase {

suspend operator fun invoke(): User
}