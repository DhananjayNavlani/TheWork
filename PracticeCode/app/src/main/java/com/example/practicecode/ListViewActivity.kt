package com.example.practicecode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.size

class ListViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        val expenses = arrayOf("Groceries","Transportation","Rent","Cell Phone","Utility Bills","Insurance")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,expenses)
        val listview = findViewById<ListView>(R.id.list_view)

        listview.adapter = adapter
        listview.setOnItemClickListener { parent, view, position, id ->
            val tv = view as TextView
            Toast.makeText(this,tv.text.toString()+ " $position $id ${parent.size}",Toast.LENGTH_LONG).show()
        }
    }
}