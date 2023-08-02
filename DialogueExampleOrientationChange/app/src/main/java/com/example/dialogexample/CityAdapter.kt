package com.example.dialogexample

import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class CityAdapter(val cityData: List<City>) : BaseAdapter() {
    override fun getCount(): Int {
        return cityData.size
    }

    override fun getItem(position: Int): City {
        return cityData[position]
    }

    override fun getItemId(position: Int): Long {
        return cityData[position].name.hashCode().toLong()
    }


    override fun getView(position: Int, convertView: View?, container: ViewGroup): View {
        val cityView: View
        val viewHolder: ViewHolder

        if(convertView == null){
            cityView = LayoutInflater.from(container.context).inflate(R.layout.city_item,
                container, false)

            viewHolder = ViewHolder()
            with(viewHolder){
                cityName = cityView.findViewById(R.id.city_name)
                cityCountry = cityView.findViewById(R.id.city_country)
                cityView.tag = this
            }
        } else{
            cityView = convertView
            viewHolder = convertView.tag as ViewHolder
        }

        with(viewHolder){
            val city = getItem(position)
            cityName.text = city.name
            cityCountry.text = city.country
        }
        return cityView
    }

    private class ViewHolder{
        lateinit var cityCountry: TextView
        lateinit var cityName: TextView
    }
}