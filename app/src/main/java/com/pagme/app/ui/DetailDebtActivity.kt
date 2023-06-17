package com.pagme.app.ui

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
import com.pagme.app.databinding.ActivityDetailDebtBinding
import com.pagme.app.data.model.Card
import com.pagme.app.data.model.Debt
import com.pagme.app.extensions.formataParaMoedaBrasileira
import com.pagme.app.data.repository.DebtRepositoryImplementation
import com.pagme.app.presentation.viewmodel.DebtViewModel
import com.pagme.app.util.DEBT_KEY_ID
import kotlinx.coroutines.launch

class DetailDebtActivity : AppCompatActivity() {

    private lateinit var debtViewModel: DebtViewModel

    private var debtId: String = ""

    private var totalPagas = 0
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
            val idDebt = intent.getStringExtra("debtID")
            debtViewModel.getDebtById(idDebt.toString()).observe(this) { debt ->
                if (debt != null) {
                    Log.d("DadosDebto", "Debt: $debt")
                    with(binding) {
                        nameBuyerDetailDebtView.text = debt.nameBuyer.toString()
                        paidInstallmentsDetailDebtView.text = debt.paidInstallments.toString()
                        remainingPlotsDetailDebtView.text = (debt.installments - debt.paidInstallments).toString()
                        valueInstallmentsDetailDebtView.text = debt.valueInstallments.toString()
                        valueOfBuyDetailDebtView.text = debt.valueBuy.toString()
                        unpaidDetailDebtView.text = (debt.valueInstallments * remainingPlotsDetailDebtView.text.toString().toInt()).toDouble().toString()


                    }
                } else {
                    Log.d("DadosDebto", "Debt não encontrado")
                }

            }
        }

    }

    private fun listeners() {
        binding.buttonPayDebtView.setOnClickListener {
            if (binding.remainingPlotsDetailDebtView.text != "0") {
                val pagas = binding.paidInstallmentsDetailDebtView.text.toString().toInt()
                totalPagas = pagas + 1
                lifecycleScope.launch {
                    Toast.makeText(this@DetailDebtActivity, "Pagou!", Toast.LENGTH_LONG).show()

                    // Atualizar os valores exibidos nos TextViews
                    with(binding) {
                    }
                }
            } else {
                Toast.makeText(this, "Todas as parcelas pagas", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        getDebts()
    }

    private fun getDebts() {
        lifecycleScope.launch {


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


            }
        }
        return super.onOptionsItemSelected(item)
    }
}