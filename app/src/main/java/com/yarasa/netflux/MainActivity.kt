package com.yarasa.netflux

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.yarasa.netflux.model.Transaction
import com.yarasa.netflux.model.TransactionType
import com.yarasa.netflux.screens.AddEditTransactionScreen
import com.yarasa.netflux.screens.MainScreen
import com.yarasa.netflux.ui.theme.NetfluxTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NetfluxTheme {
                var currentScreen by remember { mutableStateOf("main") }
                var selectedType by remember { mutableStateOf(TransactionType.EXPENSE) }
                var editingTransaction by remember { mutableStateOf<Transaction?>(null) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (currentScreen) {
                        "main" -> MainScreen(
                            modifier = Modifier.padding(innerPadding),
                            onAddClick = { type ->
                                editingTransaction = null
                                selectedType = type
                                currentScreen = "add"
                            },
                            onTransactionClick = { transaction ->
                                editingTransaction = transaction
                                selectedType = transaction.type
                                currentScreen = "add"
                            }
                        )
                        "add" -> AddEditTransactionScreen(
                            type = selectedType,
                            transaction = editingTransaction,
                            onBackClick = { currentScreen = "main" }
                        )
                    }
                }
            }
        }
    }
}