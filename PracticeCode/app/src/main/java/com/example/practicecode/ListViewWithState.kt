package com.example.practicecode

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class ListViewWithState : AppCompatActivity() {
    val cityData = fillCityData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_with_state)

        loadFavorites()

        val lv = findViewById<ListView>(R.id.list_view)
        val cityAdapter = CityAdapter(cityData)
        lv.adapter = cityAdapter

        lv.setOnItemClickListener { parent, view, position, id ->
            val city = cityAdapter.getItem(position)
            city.let {
                city.favorite = !city.favorite
                cityAdapter.notifyDataSetChanged() //this will draws changes in listview for changed data
            }

            saveFavorites()
        }
    }

    private fun loadFavorites() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val fav = sharedPref.getStringSet("Favorite_Key",null)
        fav?.forEach { favCountry->
            val city = cityData.find { it.country == favCountry  }
            city?.favorite = true
        }
    }

    private fun saveFavorites() {
        val favorites = cityData.filter { it.favorite }.map { it.country }

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putStringSet("Favorite_Key",favorites.toSet())
            commit()
        }

    }
}

private fun fillCityData():Array<City>{
    return arrayOf(
        City("Argentina","Buenos Aires"),
        City("Australia","Canberra"),
        City("Austria","Vienna"),
        City("Bangladesh", "Dhaka"),
        City("Belgium", "Brussels"),
        City("Bhutan","Thimphu"),
        City("Canada","Ottawa"),
        City("Myanmar","Rangoon"),
        City("Brazil","Brasilia"),
        City("China","Beijing"),
        City("Cambodia","Phnom Penh"),
        City("Costa Rica","San Jose")
    )
}