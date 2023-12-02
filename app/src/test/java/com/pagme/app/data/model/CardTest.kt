package com.pagme.app.data.model


import org.junit.Assert.assertEquals
import org.junit.Test

class  CardTest {

    @Test
    fun testCardProperties() {
        // Crie uma instância do Card
        val card = Card(
            idCard = "123456",
            nameCard = "My Card",
            dueDate = 15,
            synchronize = true,
            disabled = false,
            userId = "user123"
        )

        // Teste as propriedades do Card
        assertEquals("123456", card.idCard)
        assertEquals("My Card", card.nameCard)
        assertEquals(15, card.dueDate)
        assertEquals(true, card.synchronize)
        assertEquals(false, card.disabled)
        assertEquals("user123", card.userId)
    }
}
