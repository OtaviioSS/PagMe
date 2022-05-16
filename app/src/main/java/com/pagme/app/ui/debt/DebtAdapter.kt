package com.pagme.app.ui.debt

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.pagme.app.R
import com.pagme.app.business.DebtBusiness
import com.pagme.app.domain.model.Debt
import kotlinx.android.synthetic.main.item_debit.view.*


class DebtAdapter(private val debtList: ArrayList<Debt>) :
    RecyclerView.Adapter<DebtAdapter.DebtViewHolder>() {
    var debtID: String = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_debit, parent, false)
        return DebtViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DebtViewHolder, position: Int) {
        val debt = debtList[position]
        debtID = debt.idDebt.toString()
        holder.idDebt = debt.idDebt.toString()
        holder.nameBuyer.text = debt.nameBuyer.toString()
        holder.paidInstallments.text = debt.paidInstallments.toString()
        holder.remainingPlots.text = (debt.installments - debt.paidInstallments).toString()
        holder.valueInstallments.text = debt.valueInstallments.toString()
        holder.valueOfBuy.text = debt.valueBuy.toString()
        holder.unpaid.text =
            ((holder.remainingPlots.text as String).toDouble() * (holder.valueInstallments.text as String).toDouble()).toString()
        holder.btnPaid.setOnClickListener {
            val value = Integer.parseInt(holder.paidInstallments.text.toString())
            enterPayment(value, debtID)
            disableButton(holder.remainingPlots.text.toString().toInt(), holder.btnPaid)
        }
        disableButton(holder.remainingPlots.text.toString().toInt(), holder.btnPaid)
        holder.statusDebt.text = debtBussines.verifyIfCurrentDateIsGreaterThanCloseDate(debt.idCard.toString())
        colorStatus(holder.statusDebt.text.toString(),holder.statusDebt)
    }

    override fun getItemCount(): Int {
        return debtList.size
    }

    class DebtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var idDebt: String = ""
        val nameBuyer: TextView = itemView.nameBuyerItemDebitView
        val paidInstallments: TextView = itemView.paidInstallmentsItemDebitView
        val remainingPlots: TextView = itemView.remainingPlotsItemDebt
        val valueInstallments: TextView = itemView.valueInstallmentsItemDebit
        val valueOfBuy: TextView = itemView.valueOfBuyItemDebit
        val unpaid: TextView = itemView.unpaidItemDebit
        val btnPaid: Button = itemView.btnPaidItemDebt
        val debtBusiness = DebtBusiness()
        val statusDebt: TextView = itemView.statusItemDebt

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, Activity_Edit_Debt::class.java)
                intent.putExtra("idDebt", idDebt)
                itemView.context.startActivity(intent)
            }

        }
    }

    fun deleteItem(pos: Int): String {
        debtList.removeAt(pos)
        notifyItemRemoved(pos)
        return debtID

    }

    private fun enterPayment(currentValue: Int, idDebt: String) {
        val value = currentValue + 1
        debtBussines.enterOnePayment(value, idDebt)
    }

    private fun disableButton(remainingPlots: Int, button: Button) {
        if (remainingPlots <= 0) {
            button.isEnabled = false
            button.setTextColor(Color.parseColor("#ffffff"));
            button.setBackgroundColor(Color.parseColor("#aaaaaa"))
        }
    }

    private fun colorStatus(status:String,bg:TextView){
        if (status == "Fechado"){
            bg.setBackgroundColor(Color.parseColor("#99ffff00"))
        }else if (status == "Atrasado"){
            bg.setBackgroundColor(Color.parseColor("#99ff0000"))

        }
    }


}

