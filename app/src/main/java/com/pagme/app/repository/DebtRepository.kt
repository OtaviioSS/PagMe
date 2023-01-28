package com.pagme.app.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pagme.app.data.dao.DebtDao
import com.pagme.app.data.dao.DebtDao_Impl
import com.pagme.app.domain.model.Debt
import kotlinx.coroutines.flow.Flow

class DebtRepository(private val dao: DebtDao) {


    fun getAllDebtUser(userId: String): Flow<List<Debt>> {
        return dao.getAllDebtUser(userId)
    }


    suspend fun save(debt: Debt) {
        dao.save(debt)
    }

    fun getToId(id: Long): Flow<Debt> {
        return dao.getToId(id)
    }

    suspend fun deleteDebt(debt: Debt) {
        dao.deleteDebt(debt)
    }

    suspend fun update(debt: Debt){
        dao.updateDebt(debt)
    }
}