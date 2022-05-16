package com.pagme.app.data.debt

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.pagme.app.domain.model.Card
import com.pagme.app.util.DatabaseRef
import com.pagme.app.domain.model.Debt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.ArrayList
import javax.inject.Inject

class DebtRepository @Inject constructor(private val debtDataSource: DebtDataSource) {

    suspend fun getDebt():List<Debt> = debtDataSource.getDebt()

    suspend fun createDebt(debt: Debt):Debt = debtDataSource.createDebt(debt)

    suspend fun updateDebt(debt: Debt):Debt = debtDataSource.updateDebt(debt)

    suspend fun deleteDebt(idDebt:String):Boolean = debtDataSource.deleteDebt(idDebt)



}