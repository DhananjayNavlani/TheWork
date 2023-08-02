package com.example.dialogexample

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.google.gson.Gson
import kotlin.math.exp

const val EXP_KEY = "Exp_Key"
class ExpenseManager : AppCompatActivity() {
    val expenseData = mutableListOf<Expense>()
    lateinit var expenseAdapter: ExpenseAdapter
    lateinit var add_data:Button
    lateinit var reset_data:Button
    lateinit var lv:ListView
    lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_manager)

        add_data = findViewById(R.id.add_data)
        reset_data = findViewById(R.id.reset_data)
        lv = findViewById(R.id.list_view)
        tv = findViewById(R.id.total_amt)

        add_data.setOnClickListener{
            addExpense()
        }
        reset_data.setOnClickListener {
            resetExpense()
        }

        loadExpense()
        expenseAdapter = ExpenseAdapter(expenseData)
        lv.adapter  = expenseAdapter

    }

    private fun loadExpense() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val exps = sharedPref.getStringSet(EXP_KEY,null)
        val gson = Gson()
        exps?.forEach {
            expenseData.add(gson.fromJson(it,Expense::class.java))
        }
        expenseData.sortBy { it.data }
//        val sum = expenseData.sumOf { it.cost.toDouble() }
        tv.text = Expense.total(expenseData)
    }

    private fun resetExpense(){
        val builder = AlertDialog.Builder(this)
        with(builder){
            setTitle("Reset list")
            setMessage("Are you sure, u want to do this ?")
            setPositiveButton("Yes",object:DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                        expenseData.clear()
                        saveData()
                }

            })

            setNegativeButton("No"){_,_ -> }
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun saveData(){
        val gson = Gson()
        Log.d("ExpenseManager",expenseData.toString())
        val edata = expenseData.map { gson.toJson(it) }
        Log.d("ExpenseManager",edata.toString())
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
//        val sum = expenseData.sumOf { it.cost.toDouble() }

        with(sharedPref.edit()){
            putStringSet(EXP_KEY,edata.toSet())
            commit()
        }
        expenseAdapter.notifyDataSetChanged()
        tv.text = Expense.total(expenseData)
    }

    private fun addExpense() {
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val eview: View = inflater.inflate(R.layout.add_item,null)
        val mItemName = eview.findViewById<EditText>(R.id.item_name)
        val mItemCost = eview.findViewById<EditText>(R.id.item_cost)


        with(builder){
            setView(eview)
            setTitle("Add Item")
            setPositiveButton("Add",null)

            setNegativeButton("Cancel", object:DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog?.dismiss()
                }
            })

        }
        val alertDialog = builder.show()

        val posBt = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        posBt.isEnabled =false

        val textWatcher = object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                posBt.isEnabled = mItemName.text.toString().isNotEmpty() && mItemCost.text.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
                if(mItemName.text.isEmpty()) mItemName.error = "Field can't be empty"
                if(mItemCost.text.isEmpty()) mItemCost.error = "Field can't be empty"
            }

        }
        mItemName.addTextChangedListener(textWatcher)
        mItemCost.addTextChangedListener(textWatcher)

        posBt.setOnClickListener {
            val item = mItemName.text.toString()
            val cost = mItemCost.text.toString()

            if(item.isNotBlank() && cost.isNotBlank()){
                expenseData.add(Expense(item,cost.toFloat()))
                expenseData.sortBy { it.data }
                saveData()
            }
            if(posBt.isEnabled)
                alertDialog.dismiss()
        }


    }

}