package com.pagme.app.repository

import com.pagme.app.data.dao.UserDao
import com.pagme.app.domain.model.Debt
import com.pagme.app.domain.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val dao: UserDao) {



    suspend fun save(user: User) {
        dao.createUser(user)
    }

    fun getToId(id: String): Flow<User> {
        return dao.searchById(id)
    }



    suspend fun update(user: User){
        dao.update(user)
    }

    suspend fun delete(user: User) {
        dao.delete(user)
    }
}