package com.pagme.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pagme.app.R
import com.pagme.app.entity.Debt
import kotlinx.android.synthetic.main.item_debit.view.*

class DebtAdapter(private val debtList: ArrayList<Debt>) : RecyclerView.Adapter<DebtAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_debit,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val debt = debtList[position]
        holder.nameBuyer.text = debt.nameBuyer
        holder.paidInstallments.text = debt.paidInstallments.toString()
        holder.remainingPlots.text = (debt.installments - debt.paidInstallments).toString()
        holder.valueInstallments.text = debt.valueInstallments.toString()
        holder.valueOfBuy.text = debt.valueBuy.toString()
        holder.unpaid.text = ((holder.remainingPlots.text as String).toDouble() * (holder.valueInstallments.text as String).toDouble()).toString()
    }

    override fun getItemCount(): Int {
        return debtList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameBuyer : TextView = itemView.nameBuyerItemDebitView
        val paidInstallments : TextView = itemView.paidInstallmentsItemDebitView
        val remainingPlots : TextView = itemView.remainingPlotsItemDebt
        val valueInstallments : TextView = itemView.valueInstallmentsItemDebit
        val valueOfBuy : TextView = itemView.valueOfBuyItemDebit
        val unpaid : TextView = itemView.unpaidItemDebit

    }

}

