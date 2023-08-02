package com.example.practicecode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat

class CustomListViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_list_view)

        val cityData = arrayOf(City("India","New Delhi"),
            City("USA","New York"),
            City("France","Paris"),
            City("Italy","Rome"),
            City("Netherlands","Amsterdam"))

        val cities:ListView = findViewById(R.id.list_view)
        cities.adapter = CityAdapter(cityData)

    }
}

class CityAdapter(val cityData:Array<City> = arrayOf(),val cityDataL:List<City> = mutableListOf()):BaseAdapter(){

    override fun getCount(): Int {
        return cityData.size
    }

    override fun getItem(position: Int): City {
        return cityData[position]
    }

    override fun getItemId(position: Int): Long {
        //should be unique, so capitals are unique
        return cityData[position].capital.hashCode().toLong()
    }

//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        val convertView = LayoutInflater.from(parent?.context).inflate(R.layout.city_item,parent,false)
//
//        val cityCountry:TextView = convertView.findViewById(R.id.country_name)
//        val cityCaptial:TextView = convertView.findViewById(R.id.capital_name)
//
//        cityCountry.text = getItem(position).country
//        cityCaptial.text = getItem(position).capital
//
//        return convertView
//    }

    //using viewholder pattern bcoz above code consume more cpu cycles
    //Bcoz findviewbyid runs everytime in above code even if convertView contains view already (not null)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val cityView:View
        val viewholder:ViewHolder

        if(convertView == null){
            cityView = LayoutInflater.from(parent?.context).inflate(R.layout.city_item,parent,false)
            viewholder = ViewHolder()
            with(viewholder){
                cityCountry = cityView.findViewById(R.id.country_name)
                cityCapital = cityView.findViewById(R.id.capital_name)
                cityView.tag = this
            }
        }else{
            cityView = convertView
            //if convertview is already made before then its tag must be having viewholder which must be containing findviewbyid values already;
            viewholder = convertView.tag as ViewHolder
        }

        with(viewholder){
            val city = getItem(position)
            cityCountry.text = getItem(position).country
            cityCapital.text = getItem(position).capital

            if(city.favorite){
                cityView.setBackgroundColor(ContextCompat.getColor(cityView.context,R.color.colorFavorite))
            }else{
                cityView.setBackgroundColor(ContextCompat.getColor(cityView.context,android.R.color.white))
            }
        }

        return cityView
    }
}

private class ViewHolder{
    lateinit var cityCountry:TextView
    lateinit var cityCapital:TextView
}
data class City(val country:String,val capital:String,var favorite:Boolean =false)