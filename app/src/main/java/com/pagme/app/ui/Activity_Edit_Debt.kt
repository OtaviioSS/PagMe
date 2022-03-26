package com.pagme.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pagme.app.R
import com.pagme.app.entity.Debt
import kotlinx.android.synthetic.main.activity_edit_debt.*

class Activity_Edit_Debt : AppCompatActivity() {
    lateinit var idDebt:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_debt)

        val extras = intent.extras
        if (extras != null) {
            idDebt = extras.getString("idDebt").toString()
            nameBuyerEditDebt.setText(idDebt)

        }
    }

    /*fun updateDebt(){
        val debt = Debt()
        debt.idDebt = this.idDebt
        debt.installments = Integer.parseInt(installmentsEditDebt.text.toString())
        debt.valueInstallments = valueInstallmentsEditDebt.text.toString().toDouble()
        debt.nameBuyer = nameBuyerEditDebt.text.toString()
        debt.whatsapp = Integer.parseInt(whatsappEditDebt.text.toString())
        debt.nameCard = debtSnep.nameBuyer.toString()
        debt.valueBuy = debtSnep.valueBuy
        debtBussines.editDebt(debt)
    }*/
}