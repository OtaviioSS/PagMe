package com.pagme.app.data.model

import org.junit.Test
import org.junit.Assert.assertEquals
import java.util.Date

class PaymentTest {

    @Test
    fun testPaymentProperties() {
        val payment = Payment(
            paymentID = "123456",
            paymentDate = Date(),
            paymentDueDate = Date(),
            paymentValue = 100.0,
            paymentStatus = "Paid",
            debtID = "987654",
            cardID = "567890",
            userID = "abcd123"
        )

        assertEquals("123456", payment.paymentID)
        assertEquals(100.0, payment.paymentValue, 0.01)
        assertEquals("Paid", payment.paymentStatus)
        assertEquals("987654", payment.debtID)
        assertEquals("567890", payment.cardID)
        assertEquals("abcd123", payment.userID)
    }
}
