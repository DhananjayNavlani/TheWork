package com.example.dialogexample

import java.text.NumberFormat
import java.util.Currency

data class Expense(val data:String, val cost:Float){

    fun getFormattedPrice(): String = formatter.format(cost)

    companion object {
        private val formatter: NumberFormat =  NumberFormat.getCurrencyInstance()
        init {
            formatter.currency = Currency.getInstance("INR")
        }

        fun total(expenses : MutableList<Expense>) : String {
            var total: Float = 0f
            expenses.forEach {
                total += it.cost
            }
            return formatter.format(total)
        }
    }
}