package com.pagme.app.data.repository

import android.content.Context
import com.pagme.app.data.model.Contact

interface ContactRepositoryInterface {

    suspend fun getContatcs(context: Context): List<Contact>

}