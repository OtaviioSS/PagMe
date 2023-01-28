package com.pagme.app.extensions

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
        //Remove o símbolo da moeda e espaçamento pra evitar bug
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
            //ao apagar todos valores de uma só vez dava erro
            //Com a exception o valor retornado é 0.00
            BigDecimal(0)
        }
    }

    companion object {
        fun formatPrice(price: String?): String {
            //Ex - price = 2222
            //retorno = 2222.00
            val df = DecimalFormat("0.00")
            return java.lang.String.valueOf(df.format(java.lang.Double.valueOf(price)))
        }

        fun formatTextPrice(price: String?): String {
            //Ex - price = 3333.30
            //retorna formato monetário em Br = 3.333,30
            //retorna formato monetário EUA: 3,333.30
            //retornar formato monetário de alguns países europeu: 3 333,30
            val bD = BigDecimal(formatPriceSave(formatPrice(price)))
            val newFormat: String = java.lang.String.valueOf(
                NumberFormat.getCurrencyInstance(Locale.getDefault()).format(bD)
            )
            val replaceable = String.format("[%s]", currencySymbol)
            return newFormat.replace(replaceable.toRegex(), "")
        }

        fun formatPriceSave(price: String): String {
            //Ex - price = $ 5555555
            //return = 55555.55 para salvar no banco de dados
            val replaceable = String.format("[%s,.\\s]", currencySymbol)
            val cleanString = price.replace(replaceable.toRegex(), "")
            val stringBuilder = StringBuilder(cleanString.replace(" ".toRegex(), ""))
            return stringBuilder.insert(cleanString.length - 2, '.').toString()
        }

        val currencySymbol: String
            get() = NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol()
    }
}