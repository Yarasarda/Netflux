package com.yarasa.netflux.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yarasa.netflux.data.local.AppDatabase
import com.yarasa.netflux.data.local.TransactionEntity
import com.yarasa.netflux.model.Transaction
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.collections.emptyList
import com.yarasa.netflux.data.local.toDomain
import com.yarasa.netflux.model.Frequency
import com.yarasa.netflux.model.TransactionType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.math.BigDecimal


data class TransactionUiState(
    val list: List<Transaction> = emptyList(),
    val totalIncome: BigDecimal = BigDecimal.ZERO,
    val totalExpense: BigDecimal = BigDecimal.ZERO,
    val balance: BigDecimal = BigDecimal.ZERO
)

class TransactionViewmodel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val dao = db.transactionDao()

    // Veritabanından gelen akışı UI State'e dönüştürüyoruz
    val uiState: StateFlow<TransactionUiState> = dao.getAllTransactions()
        .map { entities ->
            val domainList = entities.map { it.toDomain() }

            // Hesaplamaları ana thread'i yormadan burada yapıyoruz
            val income = domainList.filter { it.type == TransactionType.INCOME }
                .map { it.value }.fold(BigDecimal.ZERO, BigDecimal::add)

            val expense = domainList.filter { it.type == TransactionType.EXPENSE }
                .map { it.value }.fold(BigDecimal.ZERO, BigDecimal::add)

            TransactionUiState(
                list = domainList,
                totalIncome = income,
                totalExpense = expense,
                balance = income.subtract(expense)
            )
        }
        .flowOn(Dispatchers.Default) // Hesaplamaları arka planda yap
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TransactionUiState()
        )

    // Yeni işlem ekleme fonksiyonu
    fun addTransaction(
        title: String,
        amount: String,
        category: String,
        frequency: Frequency,
        type: TransactionType
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val entity = TransactionEntity(
                    title = title,
                    value = BigDecimal(amount),
                    category = category,
                    frequency = frequency,
                    type = type,
                    date = System.currentTimeMillis()
                )
                dao.insertTransaction(entity)
            } catch (e: Exception) {
                // Loglama veya hata yönetimi yapılabilir
                e.printStackTrace()
            }
        }
    }
}