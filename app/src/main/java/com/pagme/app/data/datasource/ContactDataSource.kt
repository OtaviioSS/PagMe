package com.pagme.app.data.datasource

import android.content.Context
import android.provider.ContactsContract
import com.pagme.app.data.model.Contact

class ContactDataSource {


    fun getContacts(context: Context): List<Contact> {
        val contactsMap = HashMap<String, MutableList<String>>()
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
                    contactsMap[name]?.add(phone)
                } else {
                    contactsMap[name] = mutableListOf(phone)
                }
            }
            cursor.close()
        }

        for ((name, phones) in contactsMap) {
            val contact = Contact(name = name, phone = phones)
            contactsList.add(contact)
        }

        return contactsList
    }


}