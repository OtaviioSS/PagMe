package com.pagme.app.data.dao

import androidx.room.*
import com.pagme.app.domain.model.Debt
import kotlinx.coroutines.flow.Flow

@Dao
interface DebtDao {

    @Query("SELECT * FROM Debt")
    fun getAllDebt(): Flow<List<Debt>>

    @Query("SELECT * FROM Debt WHERE userId = :userId")
    fun getAllDebtUser(userId:String): Flow<List<Debt>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg debt: Debt)

    @Query("SELECT * FROM Debt WHERE idDebt = :id")
    fun getToId(id: Long): Flow<Debt>

    @Update
    suspend fun updateDebt(debt: Debt)

    @Delete
    suspend fun deleteDebt(debt: Debt)
}