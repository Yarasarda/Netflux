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
    val type: TransactionType, // Gelir mi Gider mi burada belli olacak
    val value: BigDecimal,
    val title: String,
    val date: Long,
    val frequency: Frequency, // Int yerine Enum
    val category: String
)