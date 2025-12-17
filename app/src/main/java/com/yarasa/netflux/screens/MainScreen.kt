package com.yarasa.netflux.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yarasa.netflux.data.local.TransactionEntity
import com.yarasa.netflux.model.Frequency
import com.yarasa.netflux.model.TransactionType
import com.yarasa.netflux.Utils.toCurrencyString
import java.math.BigDecimal

@Composable
fun MainScreen(
    transactions: List<TransactionEntity>,
    onTransactionClick: (TransactionEntity) -> Unit,
    onAddClick: (TransactionType) -> Unit
) {
    val totalIncome = remember(transactions) {
        transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.value }
    }
    val totalExpense = remember(transactions) {
        transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.value }
    }
    val balance = totalIncome.subtract(totalExpense)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000805))
    ) {
        OverviewCard(
            income = totalIncome,
            expense = totalExpense,
            balance = balance
        )

        Spacer(modifier = Modifier.height(16.dp))

        ActionButtonsRow(
            onIncomeClick = { onAddClick(TransactionType.INCOME) },
            onExpenseClick = { onAddClick(TransactionType.EXPENSE) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Last Adds",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(transactions) { transaction ->
                TransactionItem(
                    transaction = transaction,
                    onClick = { onTransactionClick(transaction) }
                )
            }
        }
    }
}

@Composable
fun OverviewCard(
    income: BigDecimal,
    expense: BigDecimal,
    balance: BigDecimal
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(Color(0xFF1A1A1A))
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(28.dp))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Total Balance", color = Color.Gray, style = MaterialTheme.typography.labelLarge)
        Text(
            text = balance.toCurrencyString(),
            color = Color.White,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))
        Divider(color = Color.White.copy(alpha = 0.1f))
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = "Income", color = Color.Gray, fontSize = 14.sp)
                Text(
                    text = income.toCurrencyString(TransactionType.INCOME),
                    color = Color(0xFF228B22),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "Expense", color = Color.Gray, fontSize = 14.sp)
                Text(
                    text = expense.toCurrencyString(TransactionType.EXPENSE),
                    color = Color(0xFFE33E33),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun ActionButtonsRow(onIncomeClick: () -> Unit, onExpenseClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ActionButton(text = "Add Income", icon = Icons.Default.ArrowUpward, color = Color(0xFF228B22), modifier = Modifier.weight(1f), onClick = onIncomeClick)
        ActionButton(text = "Add Expense", icon = Icons.Default.ArrowDownward, color = Color(0xFFE33E33), modifier = Modifier.weight(1f), onClick = onExpenseClick)
    }
}

@Composable
fun ActionButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color.copy(alpha = 0.15f), contentColor = color),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.5f))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        }
    }
}

@Composable
fun TransactionItem(transaction: TransactionEntity, onClick: () -> Unit) {
    val amountColor = if (transaction.type == TransactionType.INCOME) Color(0xFF228B22) else Color(0xFFE33E33)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF131313))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = transaction.title, color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
            Text(text = "${transaction.category} • ${transaction.frequency.name}", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
        }
        Text(
            text = transaction.value.toCurrencyString(transaction.type),
            color = amountColor,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    val dummyList = listOf(
        TransactionEntity(1, "Maaş", BigDecimal("12500"), Frequency.MONTHLY, "İş", TransactionType.INCOME, System.currentTimeMillis())
    )
    MainScreen(transactions = dummyList, onTransactionClick = {}, onAddClick = {})
}