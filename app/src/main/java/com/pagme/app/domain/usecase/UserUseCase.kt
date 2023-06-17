package com.pagme.app.domain.usecase

import com.pagme.app.data.model.User
import com.pagme.app.data.repository.UserRepositoryInterface

class UserUseCase(private val userRepositoryInterface: UserRepositoryInterface) {

    suspend fun create(user: User, callback: (Boolean) -> Unit) {
        try {
            userRepositoryInterface.insert(user)
            callback(true)
        } catch (e: Exception) {
            callback(false)
        }

    }

    suspend fun update(user: User, callback: (Boolean) -> Unit) {
        try {
            userRepositoryInterface.update(user)
            callback(true)
        } catch (e: Exception) {
            callback(false)
        }
    }

    suspend fun delete(callback: (Boolean) -> Unit) {
        try {
            userRepositoryInterface.delete()
            callback(true)
        } catch (e: Exception) {
            callback(false)
        }
    }

    suspend fun getById(): User? {
        return userRepositoryInterface.selectById()
    }

}