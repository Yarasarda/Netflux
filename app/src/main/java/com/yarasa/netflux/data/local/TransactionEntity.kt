package com.yarasa.netflux.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yarasa.netflux.model.Frequency
import com.yarasa.netflux.model.TransactionType
import java.math.BigDecimal

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val value: BigDecimal,
    val frequency: Frequency,
    val category: String,
    val type: TransactionType,
    val date: Long
)