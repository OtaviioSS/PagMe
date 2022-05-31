package com.pagme.app.ui.debt

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pagme.app.databinding.ItemDebitBinding
import com.pagme.app.domain.model.Debt


class DebtAdapter() : ListAdapter<Debt, DebtAdapter.DebtViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtViewHolder {
        return DebtViewHolder.create(parent)

    }

    override fun onBindViewHolder(holder: DebtViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class DebtViewHolder(private val itemBinding: ItemDebitBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(debt: Debt) {
            itemBinding.run {
                nameBuyerItemDebitView.text = debt.nameBuyer
                paidInstallmentsItemDebitView.text = debt.paidInstallments.toDouble().toString()
                remainingPlotsItemDebt.text = (debt.installments.toDouble() - debt.paidInstallments.toDouble()).toString()
                valueInstallmentsItemDebit.text = debt.valueInstallments.toDouble().toString()
                valueOfBuyItemDebit.text = debt.valueBuy.toDouble().toString()

            }

        }

        companion object {
            fun create(parent: ViewGroup): DebtAdapter.DebtViewHolder {
                val itemBinding = ItemDebitBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)

                return DebtAdapter.DebtViewHolder(itemBinding)
            }
        }

        /*  init {
              itemView.setOnClickListener {
                  val intent = Intent(itemView.context, Activity_Edit_Debt::class.java)
                  intent.putExtra("idDebt", idDebt)
                  itemView.context.startActivity(intent)
              }

          }*/
    }


    private fun enterPayment(currentValue: Int, idDebt: String) {
        val value = currentValue + 1
    }

    private fun disableButton(remainingPlots: Int, button: Button) {
        if (remainingPlots <= 0) {
            button.isEnabled = false
            button.setTextColor(Color.parseColor("#ffffff"));
            button.setBackgroundColor(Color.parseColor("#aaaaaa"))
        }
    }

    private fun colorStatus(status: String, bg: TextView) {
        if (status == "Fechado") {
            bg.setBackgroundColor(Color.parseColor("#99ffff00"))
        } else if (status == "Atrasado") {
            bg.setBackgroundColor(Color.parseColor("#99ff0000"))

        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Debt>() {
            override fun areItemsTheSame(
                oldItem: Debt,
                newItem: Debt
            ): Boolean {
                return oldItem.idDebt == newItem.idDebt
            }

            override fun areContentsTheSame(
                oldItem: Debt,
                newItem: Debt
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}

