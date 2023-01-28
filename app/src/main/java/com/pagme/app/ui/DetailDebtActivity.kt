package com.pagme.app.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.pagme.app.R
import com.pagme.app.data.AppDatabase
import com.pagme.app.databinding.ActivityDetailDebtBinding
import com.pagme.app.domain.model.Debt
import com.pagme.app.extensions.MoneyTextWatcher
import com.pagme.app.extensions.formataParaMoedaBrasileira
import com.pagme.app.repository.DebtRepository
import com.pagme.app.util.DEBT_KEY_ID
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailDebtActivity : AppCompatActivity() {

    private var debtId: Long = 0L
    private var debt: Debt? = null

    private var totalPagas = 0
    private val binding by lazy {
        ActivityDetailDebtBinding.inflate(layoutInflater)
    }


    private val repository by lazy {
        DebtRepository(
            AppDatabase.instance(this).debtDao()

        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tryLoadDebt()
        listeners()
    }

    private fun listeners() {
        binding.buttonPayDebtView.setOnClickListener {
            if (binding.remainingPlotsDetailDebtView.text != "0"){
                val pagas = binding.paidInstallmentsDetailDebtView.text.toString().toInt()
                totalPagas = pagas + 1
                val debtAtualizado = updateDebt(debt, totalPagas)
                lifecycleScope.launch {
                    repository.update(debtAtualizado)
                }
            }else{
                Toast.makeText(this,"Todas as parcelas pagas",Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun updateDebt(debt: Debt?, paidInstallments: Int): Debt {
        return debtId.let { id ->
            Debt(
                idDebt = debt!!.idDebt,
                nameCard = "",
                nameBuyer = debt.nameBuyer,
                valueBuy = debt.valueBuy,
                installments = debt.installments,
                paidInstallments = paidInstallments.toString().toInt(),
                whatsapp = debt.whatsapp,
                valueInstallments = debt.valueInstallments,
                userId = debt.userId
            )
        }

    }

    override fun onResume() {
        super.onResume()
        getDebts()
    }

    private fun getDebts() {
        lifecycleScope.launch {
            repository.getToId(debtId).collect() { debtFound ->
                debt = debtFound
                debt?.let {
                    fillFields(it)
                } ?: finish()

            }

        }
    }

    /*
    * 1 buscar os dados
    * 2 pegar as  parcelas salvas
    *
    *
    * */

    private fun tryLoadDebt() {
        debtId = intent.getLongExtra(DEBT_KEY_ID, 0L)
    }

    fun fillFields(chargedDebt: Debt) {
        with(binding) {
            nameBuyerDetailDebtView.text = chargedDebt.nameBuyer
            paidInstallmentsDetailDebtView.text = chargedDebt.paidInstallments.toString()
            remainingPlotsDetailDebtView.text =
                (chargedDebt.installments - chargedDebt.paidInstallments).toString()
            valueInstallmentsDetailDebtView.text =
                chargedDebt.valueInstallments.toBigDecimal().formataParaMoedaBrasileira()
            valueOfBuyDetailDebtView.text =
                chargedDebt.valueBuy.toBigDecimal().formataParaMoedaBrasileira()
            unpaidDetailDebtView.text = (binding.remainingPlotsDetailDebtView.text.toString()
                .toInt() * chargedDebt.valueInstallments).toBigDecimal()
                .formataParaMoedaBrasileira()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_debt, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editMenuDetailDebt -> {
                startActivity(Intent(this, FormDebtActivity::class.java).apply {
                    putExtra(DEBT_KEY_ID, debtId)

                })

            }
            R.id.deleteMenuDetailDebt -> {
                debt?.let {
                    lifecycleScope.launch {
                        repository.deleteDebt(it)
                        finish()
                    }
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }
}