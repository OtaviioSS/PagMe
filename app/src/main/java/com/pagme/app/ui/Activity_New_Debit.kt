package com.pagme.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.pagme.app.R
import com.pagme.app.business.CardBusiness
import com.pagme.app.business.DebtBusiness
import com.pagme.app.entity.Card
import com.pagme.app.entity.Debt
import kotlinx.android.synthetic.main.activity_new_debit.*
import kotlinx.android.synthetic.main.dialog_create_new_card.view.*
import java.util.*
import kotlin.toString


class Activity_New_Debit : AppCompatActivity() {
    private var debtBusiness = DebtBusiness()
    private var cardBusiness = CardBusiness()

    private var nameCard = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_debit)
        openActivityAddNewCard()
        createSpinner()
        buttonSaveNewDebtView.setOnClickListener {
            createNewDebit()

        }



        whatsappNewDebtView.addTextChangedListener(
            Mask.insert(
                "(##)#####-####",
                whatsappNewDebtView
            )
        )
        validateFields()


    }

    private fun validateFields() {
        validateEditText(nameBuyerNewDebtView, textFieldNameBuyerNewDebtView)
        validateEditText(installmentsNewDebtView, textFieldInstallmentsNewDebtView)
        validateEditText(whatsappNewDebtView, textFieldWhatsappNewDebtView)
        validateEditText(spinnerCardNewDebtView, textFieldSpinnerCardNewDebtView)
        installmentsNewDebtView.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val price = valueBuyNewDebtView.text.toString()
                valueInstallmentsNewDebtView.setText(
                    (price.toDouble() / installmentsNewDebtView.text.toString().toInt()).toString()
                )
            }


        }

//        valueBuyNewDebtView.addTextChangedListener(MoneyTextWatcher(valueBuyNewDebtView))


    }

    private fun validateEditText(editText: EditText, textInputLayout: TextInputLayout) {
        editText.setOnFocusChangeListener { v, hasFocus ->
            if (editText.text.toString().isEmpty() && !hasFocus) {
                textInputLayout.error = getString(R.string.campo_obrigatorio)
            } else {
                textInputLayout.error = null
                textInputLayout.isErrorEnabled = false
            }
        }
    }

    private fun createSpinner() {
        spinnerCardNewDebtView.setOnItemClickListener { parent, view, position, id ->
            nameCard = parent.getItemAtPosition(position).toString()
        }
        val cardsList = debtBusiness.readCardsFromSpinner()
        val cardsAdapter = ArrayAdapter(this, R.layout.dropdown_item, cardsList)
        cardsAdapter.setDropDownViewResource(R.layout.dropdown_item)
        spinnerCardNewDebtView.setAdapter(cardsAdapter)


    }

    private fun createNewDebit() {
        debtBusiness.newDebt(
            Debt(
                UUID.randomUUID().toString(),
                nameCard,
                nameBuyerNewDebtView.text.toString(),
                valueBuyNewDebtView.text.toString().toDouble(),
                installmentsNewDebtView.text.toString().toInt(),
                0,
                whatsappNewDebtView.text.toString(),
                valueInstallmentsNewDebtView.text.toString().toDouble()
            )
        )
        this.finish()

    }

    private fun openActivityAddNewCard() {
        btnOpenViewNewDebtView.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_new_card, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle(R.string.novo_cart_o)

            val mAlertDialog = mBuilder.show()
            mDialogView.dialogButtonSaveNewCardView.setOnClickListener {

                val date = Calendar.getInstance().time
                val card = Card(
                    date.toString(),
                    mDialogView.dialogNameNewCardView.text.toString(),
                    mDialogView.dialogClosingNewCardView.text.toString(),
                    mDialogView.dialogDueDateNewCardView.text.toString()
                )
                val resultBusiness = cardBusiness.createCard(card)
                if (resultBusiness) {
                    Toast.makeText(this, R.string.cartao_inserido, Toast.LENGTH_LONG).show()
                    mAlertDialog.cancel()
                }
                Toast.makeText(this, R.string.erro_inserir_cartao, Toast.LENGTH_LONG).show()


            }
        }
    }


}