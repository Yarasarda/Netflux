package com.yarasa.netflux.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yarasa.netflux.data.local.AppDatabase
import com.yarasa.netflux.model.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TransactionViewmodel(application: Application) : AndroidViewModel(application) {

    private var _transactions = MutableStateFlow<List<Transaction>>(emptyList())


    fun getTransactions(){
        viewModelScope.launch(Dispatchers.IO){


        }
    }


}