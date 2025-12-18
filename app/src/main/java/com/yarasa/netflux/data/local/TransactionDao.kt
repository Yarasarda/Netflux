package com.yarasa.netflux.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)
    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>
    @Query("SELECT * FROM transactions WHERE type = 'INCOME'")
    fun getAllIncomes(): Flow<List<TransactionEntity>>
}