package com.yarasa.netflux.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yarasa.netflux.model.Frequency
import com.yarasa.netflux.model.TransactionType
import com.yarasa.netflux.viewmodel.TransactionViewmodel

@Composable
fun AddEditTransactionScreen(
    type: TransactionType = TransactionType.EXPENSE,
    onBackClick: () -> Unit,
    viewModel: TransactionViewmodel = viewModel()
) {
    AddEditTransactionContent(
        type = type,
        onBackClick = onBackClick,
        onSaveClick = { title, amount, category, frequency ->
            viewModel.addTransaction(title, amount, category, frequency, type)
            onBackClick()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTransactionContent(
    type: TransactionType,
    onBackClick: () -> Unit,
    onSaveClick: (String, String, String, Frequency) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf(Frequency.ONE_TIME) }

    val themeColor = if (type == TransactionType.INCOME) Color(0xFF228B22) else Color(0xFFE33E33)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (type == TransactionType.INCOME) "Add Income" else "Add Expense",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (title.isNotBlank() && amount.isNotBlank()) {
                            onSaveClick(title, amount, category, frequency)
                        }
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Save", tint = themeColor)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF000805))
            )
        },
        containerColor = Color(0xFF000805)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomTextField(
                value = title,
                onValueChange = { title = it },
                label = "Title (e.g. Salary, Rent)",
                keyboardType = KeyboardType.Text
            )

            CustomTextField(
                value = amount,
                onValueChange = { amount = it },
                label = "Amount",
                keyboardType = KeyboardType.Decimal,
                prefix = { Text("₺ ", color = Color.White) }
            )

            CustomTextField(
                value = category,
                onValueChange = { category = it },
                label = "Category",
                keyboardType = KeyboardType.Text
            )

            FrequencySelector(
                selectedFrequency = frequency,
                onFrequencySelected = { frequency = it },
                color = themeColor
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (title.isNotBlank() && amount.isNotBlank()) {
                        onSaveClick(title, amount, category, frequency)
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = themeColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save Transaction", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    prefix: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        prefix = prefix,
        singleLine = true
    )
}

@Composable
fun FrequencySelector(
    selectedFrequency: Frequency,
    onFrequencySelected: (Frequency) -> Unit,
    color: Color
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedFrequency.name,
            onValueChange = {},
            readOnly = true,
            label = { Text("Frequency", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.White,
                disabledBorderColor = Color.Gray.copy(alpha = 0.5f),
                disabledLabelColor = Color.Gray
            )
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFF1A1A1A))
        ) {
            Frequency.entries.forEach { frequency ->
                DropdownMenuItem(
                    text = { Text(frequency.name, color = Color.White) },
                    onClick = {
                        onFrequencySelected(frequency)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddEditTransactionScreenPreview() {
    AddEditTransactionContent(
        type = TransactionType.EXPENSE,
        onBackClick = {},
        onSaveClick = { _, _, _, _ -> }
    )
}