package com.pagme.app.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pagme.app.R
import com.pagme.app.business.DebtBussines
import com.pagme.app.entity.Debt
import kotlinx.android.synthetic.main.activity_new_debit.*
import java.lang.Double
import java.util.*


private var database: DatabaseReference = Firebase.database.reference
private var debtBussines = DebtBussines(database)

val cards: MutableList<String?> = ArrayList()

class Activity_New_Debit : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_debit)
        openActivityAddNewCard()
        debtBussines.readCardsFromSppiner(this, spinnerCardNewDebitView);
        buttonSaveNewDebitView.setOnClickListener { createNewDabit() }

    }

    private fun createNewDabit() {
//        try {

        val date = Calendar.getInstance().time
        val debt = Debt(
            nameBuyerNewDebitView.text.toString() + spinnerCardNewDebitView.selectedItem.toString() +
                    valueBuyNewDebitView.text.toString(),
            spinnerCardNewDebitView.selectedItem.toString(),
            nameBuyerNewDebitView.text.toString(),
            Double.parseDouble(valueBuyNewDebitView.text.toString()),
            Integer.parseInt(installmentsNewDebitView.text.toString()),
            0,
            Integer.parseInt(whatsappNewDebitView.text.toString()),
            Double.parseDouble(valueInstallmentsNewDebitView.text.toString())
        )
        debtBussines.newDebit(debt)
//        }catch (e:Exception){
//            Toast.makeText(this, "Problema ao capturar dados digitados "+e, Toast.LENGTH_SHORT).show()
//
//
//        }
    }


    fun openActivityAddNewCard() {
        btnOpenViewNewDebitView.setOnClickListener() {
            val intent = Intent(this, Activity_AddCard::class.java)
            startActivity(intent)
            finish()
        }
    }


}