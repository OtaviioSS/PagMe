package com.pagme.app.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pagme.app.R
import com.pagme.app.business.DebtBussines
import com.pagme.app.entity.Debt
import com.pagme.app.repository.DebtRepository
import kotlinx.android.synthetic.main.activity_new_debit.*
import java.lang.Double
import java.util.*


private var database: DatabaseReference = Firebase.database.reference
private var debtBussines = DebtBussines(database)
private var debtRepository = DebtRepository(database)

val cards: MutableList<String?> = ArrayList()

class Activity_New_Debit : AppCompatActivity() {

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
        val cardsList = debtBussines.readCardsFromSppiner()
        val cardsAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cardsList)
        cardsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCardNewDebtView.adapter = cardsAdapter


    }

    private fun createNewDabit() {
        val debt = Debt(
            nameBuyerNewDebtView.text.toString() + spinnerCardNewDebtView.selectedItem.toString() +
                    valueBuyNewDebtView.text.toString(),
            spinnerCardNewDebtView.selectedItem.toString(),
            nameBuyerNewDebtView.text.toString(),
            Double.parseDouble(valueBuyNewDebtView.text.toString()),
            Integer.parseInt(installmentsNewDebtView.text.toString()),
            0,
            Integer.parseInt(whatsappNewDebtView.text.toString()),
            Double.parseDouble(valueInstallmentsNewDebtView.text.toString())
        )
        debtBussines.newDebit(debt)

    }


    private fun openActivityAddNewCard() {
        btnOpenViewNewDebtView.setOnClickListener() {
            val intent = Intent(this, Activity_AddCard::class.java)
            startActivity(intent)
            finish()
        }
    }


}