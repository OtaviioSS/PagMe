package com.pagme.app.data.repository

import com.pagme.app.data.datasource.UserDataSource
import com.pagme.app.data.model.Card
import com.pagme.app.data.model.User
import com.pagme.app.util.CreateUserResult

class UserRepositoryImplementation(private val userDataSource: UserDataSource) :
    UserRepositoryInterface {
    override suspend fun insert(user: User): CreateUserResult {
        try {
           return userDataSource.create(user)
        } catch (e: Exception) {
            throw Exception("Falha ao criar o usuario: ${e.message}")
        }
    }

    override suspend fun update(user: User) {
        try {
            userDataSource.alter(user)
        } catch (e: Exception) {
            throw Exception("Falha ao criar o usuario: ${e.message}")
        }
    }

    override suspend fun delete() {
        try {
            userDataSource.remove()
        } catch (e: Exception) {
            throw Exception("Falha ao criar o usuario: ${e.message}")
        }
    }

    override suspend fun selectById(): User? {
        try {
            return userDataSource.getUserById()

        } catch (e: Exception) {
            throw Exception("Falha ao criar o cartão: ${e.message}")
        }
    }

    override suspend fun userVerified(): Boolean {
        try {
            return userDataSource.userVerified()
        } catch (e: Exception) {
            throw Exception("Falha ao criar o cartão: ${e.message}")
        }
    }


}