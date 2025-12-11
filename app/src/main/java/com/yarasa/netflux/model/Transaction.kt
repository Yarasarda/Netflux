package com.yarasa.netflux.model

import java.math.BigDecimal

sealed interface Transaction {
    val value: BigDecimal
    val title: String
    val day: Int
    val frequency: Int
}