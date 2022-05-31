package com.pagme.app.ui.registeruser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pagme.app.domain.model.User
import com.pagme.app.domain.usecase.user.CreateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterUserViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _userCreated = MutableLiveData<Task<AuthResult>>()
    val userCreated: LiveData<Task<AuthResult>> = _userCreated

    fun createUser(email: String, password: String,name:String)= viewModelScope.launch {

            try {
                val user = createUserUseCase(email,password,name)
                _userCreated.value = user
            } catch (e: Exception) {
                Log.d("CreateProduct", e.toString())

        }
    }

}