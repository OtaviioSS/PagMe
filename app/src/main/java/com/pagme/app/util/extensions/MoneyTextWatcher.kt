package com.pagme.app.util.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


class MoneyTextWatcher(editText: EditText?) : TextWatcher {
    private val editTextWeakReference: WeakReference<EditText>
    private val locale: Locale = Locale.getDefault()

    init {
        editTextWeakReference = WeakReference(editText)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(editable: Editable) {
        val editText: EditText = editTextWeakReference.get() ?: return
        editText.removeTextChangedListener(this)
        val parsed: BigDecimal = parseToBigDecimal(editable.toString())
        val formatted: String = NumberFormat.getCurrencyInstance(locale).format(parsed)
        val replaceable = String.format("[%s\\s]", currencySymbol)
        val cleanString = formatted.replace(replaceable.toRegex(), "")
        editText.setText(cleanString)
        editText.setSelection(cleanString.length)
        editText.addTextChangedListener(this)
    }

    private fun parseToBigDecimal(value: String): BigDecimal {
        val replaceable = String.format("[%s,.\\s]", currencySymbol)
        val cleanString = value.replace(replaceable.toRegex(), "")
        return try {
            BigDecimal(cleanString).setScale(
                2, BigDecimal.ROUND_FLOOR
            ).divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)
        } catch (e: NumberFormatException) {
            BigDecimal(0)
        }
    }

    companion object {
        fun formatPrice(price: String?): String {
            val df = DecimalFormat("0.00")
            return java.lang.String.valueOf(df.format(java.lang.Double.valueOf(price)))
        }


        fun formatPriceSave(price: String): String {
            val replaceable = String.format("[%s,.\\s]", currencySymbol)
            val cleanString = price.replace(replaceable.toRegex(), "")
            val stringBuilder = StringBuilder(cleanString.replace(" ".toRegex(), ""))
            return stringBuilder.insert(cleanString.length - 2, '.').toString()
        }

        val currencySymbol: String
            get() = NumberFormat.getCurrencyInstance(Locale.getDefault()).currency!!.symbol
    }
}