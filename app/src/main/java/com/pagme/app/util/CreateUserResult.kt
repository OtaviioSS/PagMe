package com.pagme.app.util

sealed class CreateUserResult {
    data class Success(val message: String) : CreateUserResult()
    data class EmailExists(val message: String) : CreateUserResult()
    data class WeakPassword(val message: String) : CreateUserResult()
    data class Failure(val error: String) : CreateUserResult()
}