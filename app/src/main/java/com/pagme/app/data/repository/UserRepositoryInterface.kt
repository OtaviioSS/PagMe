package com.pagme.app.data.repository

import com.pagme.app.data.model.Card
import com.pagme.app.data.model.User
import com.pagme.app.util.CreateUserResult

interface UserRepositoryInterface {

    suspend fun insert(user: User):CreateUserResult

    suspend fun update(user: User)

    suspend fun delete()

    suspend fun selectById(): User?
    suspend fun userVerified(): Boolean



}