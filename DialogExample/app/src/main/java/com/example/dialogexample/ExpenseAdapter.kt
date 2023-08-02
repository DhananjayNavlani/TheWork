package com.example.dialogexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ExpenseAdapter(val edata:List<Expense>):BaseAdapter(){
    override fun getCount(): Int {
        return edata.size
    }

    override fun getItem(position: Int): Expense {
        return edata[position]
    }

    override fun getItemId(position: Int): Long {
        return edata[position].data.hashCode().toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val singleView:View
        val eViewHolder: EViewHolder

        if(convertView == null){
            singleView = LayoutInflater.from(parent?.context).inflate(R.layout.single_item,parent,false)
            eViewHolder = EViewHolder()

            //initializing the components of a view
            with(eViewHolder){
                mItem = singleView.findViewById(R.id.item)
                mCost = singleView.findViewById(R.id.cost)
                singleView.tag = this
            }
        }else{
            singleView = convertView
            eViewHolder = convertView.tag as EViewHolder
        }

        with(eViewHolder){
            mItem.text = getItem(position).data
            mCost.text = getItem(position).cost.toString()
        }
        return singleView
    }

}
private class EViewHolder{
    lateinit var mItem:TextView
    lateinit var mCost:TextView
}
