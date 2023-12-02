package com.pagme.app.data.model

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.UUID


class ContactTest {

    @Test
    fun testContactProprieties() {
        val contact = Contact(
            id = UUID.randomUUID().toString(),
            phone = "123456789",
            name = "Renato"
        )
        assertEquals(contact.id, contact.id)
        assertEquals(contact.phone, "123456789")
        assertEquals(contact.name, "Renato")



    }
}