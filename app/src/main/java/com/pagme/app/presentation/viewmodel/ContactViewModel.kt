package com.pagme.app.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pagme.app.data.model.Contact
import com.pagme.app.domain.usecase.ContactUseCase
import kotlinx.coroutines.launch

class ContactViewModel(private val useCase: ContactUseCase) : ViewModel() {
    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>> = _contacts

    fun getAllContacts(context: Context) {
        viewModelScope.launch {
            _contacts.value = useCase.getContacts(context)
        }
    }

}
