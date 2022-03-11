package com.pagme.app.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pagme.app.entity.Debt
import kotlinx.android.synthetic.main.item_debit.view.*

class DebtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(debt: Debt) {

        itemView.nameBuyerItemDebitView.text = debt.nameBuyer
        itemView.paidInstallmentsItemDebitView.text = debt.paidInstallments.toString()
        itemView.remainingPlotsItemDebt.text =
            (debt.installments - debt.paidInstallments).toString()
        itemView.valueInstallmentsItemDebit.text = debt.valueInstallments.toString()
        itemView.valueOfBuyItemDebit.text = debt.valueBuy.toString()
        val paidInstallments = itemView.remainingPlotsItemDebt.text.toString().toInt()
        itemView.unpaidItemDebit.text = (paidInstallments * debt.valueInstallments).toString()


    }
}