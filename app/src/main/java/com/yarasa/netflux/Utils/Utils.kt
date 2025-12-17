package com.yarasa.netflux.Utils

import com.yarasa.netflux.model.TransactionType
import java.math.BigDecimal
import java.math.RoundingMode



fun BigDecimal.toCurrencyString(type: TransactionType? = null): String {
    val formatted = this.setScale(2, RoundingMode.HALF_DOWN).toString()
    return when (type) {
        TransactionType.INCOME -> "+$formatted ₺"
        TransactionType.EXPENSE -> "-$formatted ₺"
        null -> "$formatted ₺" // Toplam bakiye için
    }
}