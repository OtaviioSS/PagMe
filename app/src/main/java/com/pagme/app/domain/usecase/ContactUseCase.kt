package com.pagme.app.domain.usecase

import android.content.Context
import com.pagme.app.data.model.Contact
import com.pagme.app.data.repository.ContactRepositoryInterface

class ContactUseCase(private val contactRepositoryInterface: ContactRepositoryInterface) {

    suspend fun getContacts(context: Context): List<Contact> {
        return contactRepositoryInterface.getContatcs(context)
    }
}