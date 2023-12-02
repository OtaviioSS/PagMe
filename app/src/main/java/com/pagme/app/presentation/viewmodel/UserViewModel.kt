package com.pagme.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pagme.app.data.model.User
import com.pagme.app.domain.usecase.UserUseCase
import com.pagme.app.util.CreateUserResult
import kotlinx.coroutines.launch

class UserViewModel(private val useCase: UserUseCase) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val user: LiveData<List<User>> = _users

    private val _createUserResult = MutableLiveData<CreateUserResult>()
    val createUserResult: LiveData<CreateUserResult> = _createUserResult

    fun create(user: User) {
        try {
            viewModelScope.launch {
                val result = useCase.create(user)
                _createUserResult.postValue(result)
            }
        }catch (e:Exception){
            _createUserResult.postValue(CreateUserResult.Failure("Erro desconhecido: ${e.message}"))

        }

    }

    fun update(user: User, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            useCase.update(user) { success ->
                callback(success)

            }
        }
    }

    fun delete(callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            useCase.delete() { success ->
                callback(success)

            }
        }
    }

    fun getById(): LiveData<User?> {
        val userLiveData = MutableLiveData<User?>()
        viewModelScope.launch {
            val user = useCase.getById()
            userLiveData.postValue(user)
        }
        return userLiveData
    }

    fun userVerified(): Boolean {
        var verified = false
        viewModelScope.launch {
            verified = useCase.userVerified()
        }
        return verified
    }
}