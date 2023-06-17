package com.pagme.app.data.repository

import android.content.Context
import com.pagme.app.data.datasource.ContactDataSource
import com.pagme.app.data.model.Contact

class ContactRepositoryImplementation(private val contactDataSource: ContactDataSource) :
    ContactRepositoryInterface {
    override suspend fun getContatcs(context: Context): List<Contact> {
        return contactDataSource.getContatcs(context)
    }
}