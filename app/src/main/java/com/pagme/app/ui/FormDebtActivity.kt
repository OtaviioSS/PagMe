package com.pagme.app.ui

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.pagme.app.R
import com.pagme.app.data.AppDatabase
import com.pagme.app.data.dao.DebtDao
import com.pagme.app.databinding.ActivityFormDebtBinding
import com.pagme.app.domain.model.Debt
import com.pagme.app.extensions.MoneyTextWatcher
import com.pagme.app.extensions.MoneyTextWatcher.Companion.formatPriceSave
import com.pagme.app.repository.DebtRepository
import com.pagme.app.util.DEBT_KEY_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.*


class FormDebtActivity : UserBaseActivity() {


    private val binding by lazy {
        ActivityFormDebtBinding.inflate(layoutInflater)
    }


    private val repository by lazy {
        DebtRepository(
            AppDatabase.instance(this).debtDao()

        )
    }



    private var debtId: Long = 0L

    var valorParcela: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastro  de Debto"
        listeners()
        tryLoadingDebt()
        binding.valueBuyNewDebtView.addTextChangedListener(MoneyTextWatcher(binding.valueBuyNewDebtView))
        lifecycleScope.launch {
            launch {
                tryGetDebt()
            }
        }
    }


    private fun tryLoadingDebt() {
        debtId = intent.getLongExtra(DEBT_KEY_ID, 0L)
    }


    private fun listeners() {

        binding.installmentsNewDebtView.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                try {
                    valorParcela = formatPriceSave(binding.valueBuyNewDebtView.text.toString())
                        .toDouble() / binding.installmentsNewDebtView.text.toString().toInt()
                    binding.valueInstallmentsNewDebtView.setText(valorParcela.toString())
                } catch (e: Exception) {
                    Toast.makeText(
                        this,
                        "Problema ao calcular valor das parcelas, tente novamente por favor",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }

        binding.buttonSaveNewDebtView.setOnClickListener {
            lifecycleScope.launch {
                var userID: String = ""
                user.firstOrNull()?.let { user ->
                    if (debtId != 0L) {
                        val debt = updateDebt(debtId, user.userId)
                        repository.update(debt!!)
                        Toast.makeText(
                            this@FormDebtActivity,
                            "Divida atualizada",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        finish()

                    } else {
                        userID = user.userId
                        val newDebt = createDebt(user.userId)
                        repository.save(newDebt!!)
                        Toast.makeText(this@FormDebtActivity, "Divida criada", Toast.LENGTH_LONG)
                            .show()
                        finish()
                    }

                }


            }
        }
    }

    private fun updateDebt(debtId: Long?, userID: String): Debt? {
        return debtId?.let { id ->
            Debt(
                idDebt = debtId,
                nameCard = "",
                nameBuyer = binding.nameBuyerNewDebtView.text.toString(),
                valueBuy = formatPriceSave(binding.valueBuyNewDebtView.text.toString()).toDouble(),
                installments = binding.installmentsNewDebtView.text.toString().toInt(),
                paidInstallments = 0,
                whatsapp = binding.whatsappNewDebtView.text.toString(),
                valueInstallments = valorParcela,
                userId = userID
            )
        }
    }


    private suspend fun tryGetDebt() {
        debtId.let { id ->
            repository.getToId(id)
                .filterNotNull()
                .collect { debtFound ->
                    debtId = debtFound.idDebt
                    binding.valueBuyNewDebtView.setText(debtFound.valueBuy.toString())
                    binding.installmentsNewDebtView.setText(debtFound.installments.toString())
                    binding.valueInstallmentsNewDebtView.setText(debtFound.valueInstallments.toString())
                    binding.nameBuyerNewDebtView.setText(debtFound.nameBuyer)
                    binding.whatsappNewDebtView.setText(debtFound.whatsapp)
                }

        }

    }


    private fun createDebt(userID: String): Debt {
        return debtId.let { id ->
            Debt(
                idDebt = Random().nextLong(),
                nameCard = "",
                nameBuyer = binding.nameBuyerNewDebtView.text.toString(),
                valueBuy = formatPriceSave(binding.valueBuyNewDebtView.text.toString()).toDouble(),
                installments = binding.installmentsNewDebtView.text.toString().toInt(),
                paidInstallments = 0,
                whatsapp = binding.whatsappNewDebtView.text.toString(),
                valueInstallments = valorParcela,
                userId = userID
            )
        }

    }


}