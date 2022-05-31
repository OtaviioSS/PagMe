package com.pagme.app.ui.login

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.pagme.app.domain.model.User
import com.pagme.app.domain.usecase.user.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _userLogged = MutableLiveData<Task<AuthResult>>()
    val userLogged: LiveData<Task<AuthResult>> = _userLogged

    fun uerLogin(email: String, password: String) = viewModelScope.launch {
         try {
                val user = loginUseCase(email,password)
                _userLogged.value = user
            } catch (e: Exception) {
                Log.d("CreateProduct", e.toString())
            }

    }



}