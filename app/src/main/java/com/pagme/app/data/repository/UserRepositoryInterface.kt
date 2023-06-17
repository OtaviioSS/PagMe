package com.pagme.app.data.repository

import com.pagme.app.data.model.Card
import com.pagme.app.data.model.User

interface UserRepositoryInterface {

    suspend fun insert(user: User)

    suspend fun update(user: User)

    suspend fun delete()

    suspend fun selectById(): User?

}