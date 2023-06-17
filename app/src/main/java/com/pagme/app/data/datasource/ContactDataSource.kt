package com.pagme.app.data.datasource

import android.content.Context
import android.provider.ContactsContract
import com.pagme.app.data.model.Card
import com.pagme.app.data.model.Contact
import kotlinx.coroutines.tasks.await

class ContactDataSource {

    fun getContatcs(context: Context): List<Contact> {
        val contactsMap = HashMap<String, String>()
        val contactsList = mutableListOf<Contact>()
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC"
        )
        cursor?.let {
            while (cursor.moveToNext()) {
                val id =
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))
                val phone =
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val name =
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))

                if (contactsMap.containsKey(name)) {
                    contactsMap[name] = "${contactsMap[name]}, $phone"
                } else {
                    contactsMap[name] = phone
                }
            }
            cursor.close()
        }

        for ((name, phone) in contactsMap) {
            contactsList.add(Contact("", name, phone))
        }

        return contactsList
    }

}