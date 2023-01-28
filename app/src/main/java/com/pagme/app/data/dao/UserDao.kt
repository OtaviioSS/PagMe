package com.pagme.app.data.dao


import androidx.room.*
import com.pagme.app.domain.model.Debt
import com.pagme.app.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {


    @Insert
    suspend fun createUser(user: User)

    @Query("SELECT * FROM User WHERE userId = :userId")
    fun searchById(userId: String): Flow<User>

    @Query(
        """
        SELECT * FROM User
        WHERE email = :email
        AND userPassword = :password"""
    )
    suspend fun authenticateUser(email: String, password: String): User?


    @Update
    suspend fun update(user:User)

    @Delete
    suspend fun delete(user: User)

}