package com.yarasa.netflux.model

import java.math.BigDecimal

data class Income(
    override val value: BigDecimal,
    override val title: String,
    override val day: Int,
    override val frequency: Int,
    val source: String
) : Transaction {

}
