package com.pagme.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pagme.app.R
import com.pagme.app.business.CardBussines
import com.pagme.app.business.DebtBusines
import com.pagme.app.entity.Card
import com.pagme.app.entity.Debt
import com.pagme.app.repository.DebtRepository
import kotlinx.android.synthetic.main.activity_new_debit.*
import kotlinx.android.synthetic.main.dialog_create_new_card.view.*
import java.lang.Double
import java.util.*


private var database: DatabaseReference = Firebase.database.reference
private var debtBussines = DebtBusines()
private var debtRepository = DebtRepository()
private var cardBussines = CardBussines()


val cards: MutableList<String?> = ArrayList()

class Activity_New_Debit : AppCompatActivity() {

    private var nameCard= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_debit)
        openActivityAddNewCard()
        createSppiner()
        buttonSaveNewDebtView.setOnClickListener {
            createNewDabit()

        }


    }

    private fun createSppiner() {
        spinnerCardNewDebtView.setOnItemClickListener { parent, view, position, id ->
            nameCard = parent.getItemAtPosition(position).toString()
        }
        val cardsList = debtBussines.readCardsFromSpinner()
        val cardsAdapter = ArrayAdapter(this, R.layout.dropdown_item, cardsList)
        cardsAdapter.setDropDownViewResource(R.layout.dropdown_item)
        spinnerCardNewDebtView.setAdapter(cardsAdapter)


    }

    private fun createNewDabit() {
        val debt = Debt(
            nameBuyerNewDebtView.text.toString() + nameCard +
                    valueBuyNewDebtView.text.toString(),
            nameCard,
            nameBuyerNewDebtView.text.toString(),
            Double.parseDouble(valueBuyNewDebtView.text.toString()),
            Integer.parseInt(installmentsNewDebtView.text.toString()),
            0,
            whatsappNewDebtView.text.toString(),
            Double.parseDouble(valueInstallmentsNewDebtView.text.toString())
        )
        debtBussines.newDebt(debt)

    }


    private fun openActivityAddNewCard() {
        btnOpenViewNewDebtView.setOnClickListener() {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_new_card, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Novo Cartão")

            val mAlertDialog = mBuilder.show()
            mDialogView.dialogButtonSaveNewCardView.setOnClickListener {

                val date = Calendar.getInstance().time
                val card = Card(
                    date.toString(),
                    mDialogView.dialogNameNewCardView.text.toString(),
                    mDialogView.dialogClosingNewCardView.text.toString(),
                    mDialogView.dialogDueDateNewCardView.text.toString()
                )
                val resultBussines = cardBussines.createCard(card)
                if (resultBussines){
                    Toast.makeText(this,R.string.cartao_inserido, Toast.LENGTH_LONG).show()
                }
                Toast.makeText(this,R.string.erro_inserir_cartao, Toast.LENGTH_LONG).show()





            }
        }
    }


}