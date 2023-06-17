package com.pagme.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pagme.app.domain.usecase.ContactUseCase
import javax.inject.Inject

class ContactViewModelFactory @Inject constructor(private val useCase: ContactUseCase):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            return ContactViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}