package com.example.practicecode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

class ExpenseListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_list)

        val elist = arrayOf(Expense("Groceries",5000f),
            Expense("Transportation",8000f),Expense("Rent",50000f),
            Expense("Cell Phone",800f),Expense("Utility Bills",6500f),
            Expense("Insurance",5000f))

        val lv:ListView = findViewById(R.id.list_view)
        lv.adapter = ExpenseListAdapter(elist)

        val tv:TextView = findViewById(R.id.total_amt)
        var sum =0f
        for(i in elist.indices)
            sum+= elist[i].cost
        tv.text = "₹ $sum"
    }
}
class ExpenseListAdapter(val elist:Array<Expense>):BaseAdapter(){
    override fun getCount(): Int {
        return elist.size
    }

    override fun getItem(position: Int): Expense {
        return elist[position]
    }

    override fun getItemId(position: Int): Long {
        return elist[position].item.hashCode().toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val expenseView:View
        val viewholder:ExpenseViewHolder

        if(convertView == null){
            expenseView = LayoutInflater.from(parent?.context).inflate(R.layout.list_expense,parent,false)
            viewholder = ExpenseViewHolder()
            with(viewholder){
                item = expenseView.findViewById(R.id.item)
                cost = expenseView.findViewById(R.id.cost)
                expenseView.tag = this
            }
        }else{
            expenseView = convertView
            viewholder = convertView.tag as ExpenseViewHolder
        }

        with(viewholder){
            item.text = getItem(position).item
            cost.text = "₹"+getItem(position).cost.toString()
        }
        return expenseView
    }
}
private class ExpenseViewHolder{
    lateinit var item:TextView
    lateinit var cost:TextView
}
data class Expense(val item:String,val cost:Float)