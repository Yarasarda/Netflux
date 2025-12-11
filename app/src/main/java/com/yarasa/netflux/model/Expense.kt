package com.yarasa.netflux.model

import java.math.BigDecimal

data class Expense(
    override val value: BigDecimal,
    override val title: String,
    override val day: Int,
    override val frequency: Int,
    val category: String
) : Transaction {

}