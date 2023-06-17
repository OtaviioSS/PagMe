package com.pagme.app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pagme.app.data.model.Debt
import com.pagme.app.databinding.ItemDebitBinding
import com.pagme.app.extensions.formataParaMoedaBrasileira

class DebtAdapter(
    private var debtList: List<Debt>,
    private var filteredDebtList: List<Debt>

) :
    RecyclerView.Adapter<DebtAdapter.DebtViewHolder>() {

    fun filterList(query: String) {
        filteredDebtList = debtList.filter { debt ->
            // Aqui você pode definir a lógica de filtragem
            // Neste exemplo, estou verificando se o nome do devedor contém a query de pesquisa
            debt.nameBuyer.contains(query, ignoreCase = true)
        }
        notifyDataSetChanged()
    }
    fun setDebts(debts: List<Debt>) {
        this.debtList = debts
        filterList("")
    }
    interface OnItemClickListener {
        fun onItemClick(debt: Debt)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtViewHolder {
        return DebtViewHolder(
            ItemDebitBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return filteredDebtList.size
    }

    override fun onBindViewHolder(holder: DebtViewHolder, position: Int) {
        val debt = filteredDebtList[position]
        holder.bind(debt)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(debt)
        }

    }


    class DebtViewHolder(private val binding: ItemDebitBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(debt: Debt) {
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



}