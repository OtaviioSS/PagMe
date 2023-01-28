package com.pagme.app.ui.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pagme.app.databinding.ItemDebitBinding
import com.pagme.app.domain.model.Debt
import com.pagme.app.extensions.formataParaMoedaBrasileira

class ListDebtsAdapter(
    private val context: Context,
    debts: List<Debt> = emptyList(),
    var clickItem: (debt: Debt) -> Unit = {}
) : RecyclerView.Adapter<ListDebtsAdapter.ViewHolder>() {

    private val debts = debts.toMutableList()

    inner class ViewHolder(private val binding: ItemDebitBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var debt: Debt

        init {
            itemView.setOnClickListener {
                if (::debt.isInitialized) {
                    clickItem(debt)
                }
            }
        }

        fun bind(debt: Debt) {
            this.debt = debt
            binding.nameBuyerItemDebitView.text = debt.nameBuyer
            binding.paidInstallmentsItemDebitView.text = debt.paidInstallments.toString()
            binding.remainingPlotsItemDebt.text =
                (debt.installments - debt.paidInstallments).toString()
            binding.valueInstallmentsItemDebit.text =
                debt.valueInstallments.toBigDecimal().formataParaMoedaBrasileira().toString()
            binding.valueOfBuyItemDebit.text =
                debt.valueBuy.toBigDecimal().formataParaMoedaBrasileira()
            binding.unpaidItemDebit.text = (binding.remainingPlotsItemDebt.text.toString()
                .toInt() * debt.valueInstallments).toBigDecimal().formataParaMoedaBrasileira()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemDebitBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(debts[position])
    }

    override fun getItemCount(): Int = debts.size

    fun update(debts: List<Debt>) {
        this.debts.clear()
        this.debts.addAll(debts)
        notifyDataSetChanged()

    }
}