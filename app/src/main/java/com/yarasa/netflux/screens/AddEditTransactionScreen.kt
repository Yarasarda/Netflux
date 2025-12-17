package com.yarasa.netflux.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yarasa.netflux.model.Frequency
import com.yarasa.netflux.model.TransactionType
import com.yarasa.netflux.viewmodel.TransactionViewmodel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTransactionScreen(
    onBackClick: () -> Unit,
) {

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
            Frequency.values().forEach { frequency ->
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
    AddEditTransactionScreen(onBackClick = {})
}