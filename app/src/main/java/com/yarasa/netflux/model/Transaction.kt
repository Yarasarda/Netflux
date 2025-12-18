package com.yarasa.netflux.model

import java.math.BigDecimal

enum class TransactionType {
    INCOME, EXPENSE
}
enum class Frequency {
    ONE_TIME, DAILY, WEEKLY, MONTHLY, YEARLY
}
data class Transaction(
    val id: Int = 0,
    val type: TransactionType,
    val value: BigDecimal,
    val title: String,
    val date: Long,
    val frequency: Frequency,
    val category: String
)