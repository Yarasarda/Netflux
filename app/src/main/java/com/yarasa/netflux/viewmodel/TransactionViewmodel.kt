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
import kotlinx.coroutines.flow.firstOrNull
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

    val uiState: StateFlow<TransactionUiState> = dao.getAllTransactions()
        .map { entities ->
            val domainList = entities.map { it.toDomain() }
            val income = domainList.filter { it.type == TransactionType.INCOME }.map { it.value }.fold(BigDecimal.ZERO, BigDecimal::add)
            val expense = domainList.filter { it.type == TransactionType.EXPENSE }.map { it.value }.fold(BigDecimal.ZERO, BigDecimal::add)

            TransactionUiState(
                list = domainList,
                totalIncome = income,
                totalExpense = expense,
                balance = income.subtract(expense)
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TransactionUiState())

    fun addTransaction(title: String, amount: String, category: String, frequency: Frequency, type: TransactionType) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertTransaction(TransactionEntity(title = title, value = BigDecimal(amount), category = category, frequency = frequency, type = type, date = System.currentTimeMillis()))
        }
    }

    fun updateTransaction(transaction: Transaction, title: String, amount: String, category: String, frequency: Frequency) {
        viewModelScope.launch(Dispatchers.IO) {
            val updated = TransactionEntity(
                id = transaction.id,
                title = title,
                value = BigDecimal(amount),
                category = category,
                frequency = frequency,
                type = transaction.type,
                date = transaction.date
            )
            dao.insertTransaction(updated)
        }
    }

    fun deleteTransaction(transactionId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.getAllTransactions().firstOrNull()?.find { it.id == transactionId }?.let {
                dao.deleteTransaction(it)
            }
        }
    }
}