package com.pagme.app.ui.newdebt

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.pagme.app.R
import com.pagme.app.business.CardBusiness
import com.pagme.app.business.DebtBusiness
import com.pagme.app.domain.model.Card
import com.pagme.app.domain.model.Debt
import com.pagme.app.ui.debt.DebtViewModel
import com.pagme.app.util.Mask
import kotlinx.android.synthetic.main.activity_new_debit.*
import kotlinx.android.synthetic.main.dialog_create_new_card.view.*
import java.util.*


class Activity_New_Debit : AppCompatActivity() {
    private var debtBusiness = DebtBusiness()
    private var cardBusiness = CardBusiness()

    private var nameCard = ""

    private lateinit var viewModel: ViewModelNewDebt


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_debit)
        openActivityAddNewCard()
        createSpinner()
        buttonSaveNewDebtView.setOnClickListener {
            createDebt()
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

    private fun createDebt() {

        viewModel.createDebt(
            nameCard,
            nameBuyerNewDebtView.text.toString(),
            valueBuyNewDebtView.text.toString().toDouble(),
            installmentsNewDebtView.text.toString().toInt(),
            0,
            whatsappNewDebtView.text.toString(),
            valueInstallmentsNewDebtView.text.toString().toDouble(),
            "DEFAULT"
        )

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
                valueInstallmentsNewDebtView.text.toString().toDouble(),
                "DEFAULT"
            )
        )
        this.finish()

    }

    private fun openActivityAddNewCard() {
        btnOpenViewNewDebtView.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(this).inflate(R.layout.dialog_create_new_card, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle(R.string.novo_cart_o)

            val mAlertDialog = mBuilder.show()
            mDialogView.dialogButtonSaveNewCardView.setOnClickListener {
                viewModel.createCard(
                    mDialogView.dialogNameNewCardView.text.toString(),
                    mDialogView.dialogClosingNewCardView.text.toString(),
                    mDialogView.dialogDueDateNewCardView.text.toString()
                )
                /*val resultBusiness = cardBusiness.createCard(card)
                if (resultBusiness) {
                    Toast.makeText(this, R.string.cartao_inserido, Toast.LENGTH_LONG).show()
                    mAlertDialog.cancel()
                }
                Toast.makeText(this, R.string.erro_inserir_cartao, Toast.LENGTH_LONG).show()*/


            }
        }
    }


}