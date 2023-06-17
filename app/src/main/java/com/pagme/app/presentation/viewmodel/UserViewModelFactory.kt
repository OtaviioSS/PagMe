package com.pagme.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pagme.app.domain.usecase.UserUseCase
import javax.inject.Inject

class UserViewModelFactory @Inject constructor(private val useCase: UserUseCase):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}