package com.pagme.app.util

import android.widget.EditText
import android.text.TextWatcher
import android.text.Editable
import com.google.android.material.textfield.TextInputEditText
import java.lang.NumberFormatException
import java.lang.StringBuilder
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class CurrencyTextWatcher(private val textInputEditText: TextInputEditText, val symbol: String) : TextWatcher {
    private var current = ""
    private var editing = false

    override fun afterTextChanged(s: Editable) {}

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (!editing) {
            editing = true
            val cleanString = s.toString().replace("[^\\d]".toRegex(), "")
            val parsed = cleanString.toDoubleOrNull() ?: 0.0
            val currencyFormat = NumberFormat.getCurrencyInstance().apply {
                currency = Currency.getInstance("BRL") // Definir a moeda como Real brasileiro
                maximumFractionDigits = 2 // Definir a quantidade máxima de casas decimais
                minimumFractionDigits = 2 // Definir a quantidade mínima de casas decimais
                isGroupingUsed = true // Habilitar o separador de milhares
            }
            val formatted = currencyFormat.format(parsed / 100)
            current = formatted
            val withSymbol = if (!formatted.startsWith("R$")) {
                "R$ $formatted"
            } else {
                formatted
            }


        }
    }
}
