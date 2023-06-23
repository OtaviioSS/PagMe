package com.pagme.app.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.pagme.app.MyApplication
import com.pagme.app.R
import com.pagme.app.data.model.Debt
import com.pagme.app.databinding.ActivityDetailDebtBinding
import com.pagme.app.presentation.viewmodel.DebtViewModel
import com.pagme.app.util.DEBT_KEY_ID
import kotlinx.coroutines.launch

class DetailDebtActivity : AppCompatActivity() {

    private lateinit var debtViewModel: DebtViewModel

    private lateinit var currentDebt: Debt

    private lateinit var debtId: String

    private val binding by lazy {
        ActivityDetailDebtBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val appComponent = (application as MyApplication).appComponent
        appComponent.inject(this)
        val viewModelFactory = appComponent.provideDebtViewModelFactory()
        debtViewModel = ViewModelProvider(this, viewModelFactory)[DebtViewModel::class.java]
        tryLoadDebt()
        listeners()

        if (intent.hasExtra("debtID")) {
            debtId = intent.getStringExtra("debtID").toString()
            debtViewModel.getDebtById(debtId).observe(this) { debtFlow ->

                lifecycleScope.launch {
                    debtFlow.collect { debt ->
                        if (debt != null) {
                            currentDebt = debt
                            with(binding) {
                                nameBuyerDetailDebtView.text = debt.nameBuyer
                                paidInstallmentsDetailDebtView.text =
                                    debt.paidInstallments.toString()
                                installmentsDetailDebtView.text = debt.installments.toString()
                                remainingPlotsDetailDebtView.text =
                                    (debt.installments - debt.paidInstallments).toString()
                                valueInstallmentsDetailDebtView.text =
                                    debt.valueInstallments.toString()
                                valueOfBuyDetailDebtView.text = debt.valueBuy.toString()
                                unpaidDetailDebtView.text =
                                    (debt.valueInstallments * remainingPlotsDetailDebtView.text.toString()
                                        .toInt()).toString()
                            }
                        } else {
                            Toast.makeText(
                                this@DetailDebtActivity,
                                "Tivemos um problema para carregar o débito, tente novamente!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun listeners() {
        binding.buttonPayDetailDebtView.setOnClickListener {
            if (binding.remainingPlotsDetailDebtView.text == "0") {
                Toast.makeText(this, "Todas as parcelas foram pagas!", Toast.LENGTH_LONG).show()
            } else {
                debtViewModel.pagouParcela(currentDebt) { success ->
                    if (!success) {
                        Toast.makeText(
                            this,
                            "Não foi possível atualizar a dívida",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(this, "Pagou!", Toast.LENGTH_LONG).show()
                        Log.i("DetailDebtAty:", currentDebt.toString())
                    }

                }
            }


        }
    }


    private fun tryLoadDebt() {
        debtId = intent.getStringExtra(DEBT_KEY_ID).toString()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_debt, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editMenuDetailDebt -> {
                startActivity(Intent(this, EditFormDebtActivity::class.java).apply {
                    putExtra("debtID", debtId)

                })

            }

            R.id.deleteMenuDetailDebt -> {
                try {
                    debtViewModel.delete(debtId)
                    Toast.makeText(this, "Divida removida!", Toast.LENGTH_LONG).show()
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this, "Erro ao apagar divida!", Toast.LENGTH_LONG).show()
                }


            }
        }
        return super.onOptionsItemSelected(item)
    }
}