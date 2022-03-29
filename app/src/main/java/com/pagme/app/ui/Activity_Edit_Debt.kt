package com.pagme.app.ui

import android.os.Bundle
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

class Activity_Edit_Debt : AppCompatActivity() {
    private  var idDebt =""
    private var debtBusines = DebtBusines()
    private val database = DatabaseRef().initializeDatabaseRefrence()
    var debt = Debt()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_debt)


        getDebt()
        buttonSaveEditDebt.setOnClickListener { updateDebt() }

        val extras = intent.extras
        if (extras != null) {
            idDebt = extras.getString("idDebt").toString()
            nameBuyerEditDebt.setText(idDebt)
        }
    }

    private fun getDebt() {
        database.child("userOtavio").child("debts").child(idDebt).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (debtSnapshot in snapshot.children){
                    debt = debtSnapshot.getValue(Debt::class.java)!!

                }
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
         debt.nameCard = debt.nameCard.toString()
         debt.valueBuy = valueBuyEditDebt.text.toString().toDouble()
        debtBusines.editDebt(debt)
        Toast.makeText(this,"Divida editada",Toast.LENGTH_LONG).show()
    }
}