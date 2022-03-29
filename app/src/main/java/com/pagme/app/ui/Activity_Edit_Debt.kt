package com.pagme.app.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pagme.app.R
import com.pagme.app.business.DebtBusines
import com.pagme.app.database.DatabaseRef
import com.pagme.app.entity.Debt
import kotlinx.android.synthetic.main.activity_edit_debt.*
import kotlinx.android.synthetic.main.activity_new_debit.*

class Activity_Edit_Debt : AppCompatActivity() {
    private var idDebt = ""
    private var debtBusines = DebtBusines()
    private val database = DatabaseRef().initializeDatabaseRefrence()
    var debt = Debt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_debt)


        getDebt()
        buttonSaveEditDebt.setOnClickListener { updateDebt() }
        buttonDeleteEditDebt.setOnClickListener { removeDebt() }
        val extras = intent.extras
        if (extras != null) {
            idDebt = extras.getString("idDebt").toString()
            nameBuyerEditDebt.setText(idDebt)
        }
        createSpinnerEditDebt()
    }

    private fun getDebt() {
        database.child("userOtavio").child("debts").child(idDebt).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (debtSnapshot in snapshot.children) {
                    debt = debtSnapshot.getValue(Debt::class.java)!!

                }
                spinnerCardEditDebt.setText(debt.nameCard.toString(), false)
                valueBuyEditDebt.setText(debt.valueBuy.toString())
                installmentsEditDebt.setText(debt.installments.toString())
                valueInstallmentsEditDebt.setText(debt.valueInstallments.toString())
                nameBuyerEditDebt.setText(debt.nameBuyer)
                whatsappEditDebt.setText(debt.whatsapp.toString())


            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    private fun updateDebt() {
        val debt = Debt()
        debt.idDebt = this.idDebt
        debt.installments = Integer.parseInt(installmentsEditDebt.text.toString())
        debt.valueInstallments = valueInstallmentsEditDebt.text.toString().toDouble()
        debt.nameBuyer = nameBuyerEditDebt.text.toString()
        debt.whatsapp = whatsappEditDebt.text.toString()
        debt.nameCard = spinnerCardEditDebt.text.toString()
        debt.valueBuy = valueBuyEditDebt.text.toString().toDouble()
        debtBusines.editDebt(debt)
        Toast.makeText(this, "Divida editada", Toast.LENGTH_LONG).show()
    }

    private fun removeDebt() {
        debtBusines.removeDebt(debt.idDebt.toString())
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun createSpinnerEditDebt() {
        spinnerCardEditDebt.setOnItemClickListener { parent, view, position, id ->
            val nameCard = parent.getItemAtPosition(position).toString()
        }
        val cardsList = debtBusines.readCardsFromSpinner()
        val cardsAdapter = ArrayAdapter(this, R.layout.dropdown_item, cardsList)
        cardsAdapter.setDropDownViewResource(R.layout.dropdown_item)
        spinnerCardEditDebt.setAdapter(cardsAdapter)


    }

}