package com.pagme.app.ui.debt

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.pagme.app.R
import com.pagme.app.business.DebtBusiness
import com.pagme.app.util.DatabaseRef
import com.pagme.app.domain.model.Debt
import com.pagme.app.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_edit_debt.*

@AndroidEntryPoint
class Activity_Edit_Debt : AppCompatActivity() {
    private var idDebt = ""
    private var debtBusiness = DebtBusiness()
    private val database = DatabaseRef().initializeDatabaseRefrence()
    private var auth: FirebaseAuth = Firebase.auth
    private val user = auth.currentUser

    var debt = Debt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_debt)
        buttonSaveEditDebt.setOnClickListener { updateDebt() }
        buttonDeleteEditDebt.setOnClickListener { removeDebt() }
        val extras = intent.extras
        if (extras != null) {
            idDebt = extras.getString("idDebt").toString()
            nameBuyerEditDebt.setText(idDebt)
        }
        getDebt()
        createSpinnerEditDebt()
    }

    private fun getDebt() {
        database.child(user!!.uid.toString()).child("debts").child(idDebt)
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    debt = snapshot.getValue(Debt::class.java)!!
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
        debtBusiness.editDebt(debt)
        Toast.makeText(this, "Divida editada", Toast.LENGTH_LONG).show()
    }

    private fun removeDebt() {
        debtBusiness.removeDebt(debt.idDebt.toString())
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun createSpinnerEditDebt() {
        spinnerCardEditDebt.setOnItemClickListener { parent, view, position, id ->
            val nameCard = parent.getItemAtPosition(position).toString()
        }
        val cardsList = debtBusiness.readCardsFromSpinner()
        val cardsAdapter = ArrayAdapter(this, R.layout.dropdown_item, cardsList)
        cardsAdapter.setDropDownViewResource(R.layout.dropdown_item)
        spinnerCardEditDebt.setAdapter(cardsAdapter)


    }

}