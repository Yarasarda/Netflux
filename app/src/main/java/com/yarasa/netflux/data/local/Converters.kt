package com.yarasa.netflux.data.local

import androidx.room.TypeConverter
import com.yarasa.netflux.model.TransactionType
import com.yarasa.netflux.model.Frequency
import java.math.BigDecimal

class Converters {
    // BigDecimal <-> String
    @TypeConverter
    fun fromBigDecimal(value: BigDecimal?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toBigDecimal(value: String?): BigDecimal? {
        return value?.let { BigDecimal(it) }
    }

    // Enum <-> String
    @TypeConverter
    fun fromTransactionType(type: TransactionType): String {
        return type.name
    }

    @TypeConverter
    fun toTransactionType(value: String): TransactionType {
        return TransactionType.valueOf(value)
    }

    // Frequency Enum <-> String
    @TypeConverter
    fun fromFrequency(freq: Frequency): String {
        return freq.name
    }

    @TypeConverter
    fun toFrequency(value: String): Frequency {
        return Frequency.valueOf(value)
    }
}