package com.example.shoppingcart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CheckoutViewModelFactory(private val id: Int, private val quantity: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CheckoutViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CheckoutViewModel(id,quantity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}