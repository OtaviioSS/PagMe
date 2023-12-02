package com.pagme.app.data.model
import org.junit.Assert.assertEquals
import org.junit.Test

class DebtTest {

    @Test
    fun testDebtProperties() {
        // Criação de instância da classe Debt com valores iniciais
        val debt = Debt(
            idDebt = "",
            nameCard = "",
            cardID = "",
            nameBuyer = "",
            valueBuy = 0.0,
            installments = 0,
            paidInstallments = 0,
            whatsapp = null,
            disabled = false,
            valueInstallments = 0.0,
            userId = ""
        )

        // Verificação das propriedades
        assertEquals("", debt.idDebt)
        assertEquals("", debt.nameCard)
        assertEquals("", debt.cardID)
        assertEquals("", debt.nameBuyer)
        assertEquals(0.0, debt.valueBuy, 0.001)
        assertEquals(0, debt.installments)
        assertEquals(0, debt.paidInstallments)
        assertEquals(null, debt.whatsapp)
        assertEquals(false, debt.disabled)
        assertEquals(0.0, debt.valueInstallments, 0.001)
        assertEquals("", debt.userId)
    }
}
