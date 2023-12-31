package com.example.dialogexample

import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson


const val CITY_KEY = "CITY_KEY"

class MainActivity : AppCompatActivity() {
    val cityData = mutableListOf<City>()
    lateinit var cityAdapter: CityAdapter
    lateinit var reset_list:Button
    lateinit var add_city:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reset_list = findViewById(R.id.reset_list)
        add_city = findViewById(R.id.add_city)
        reset_list.setOnClickListener {
            resetList()
        }

        add_city.setOnClickListener {
            addCity()
        }

        loadCities()

        val cities: ListView = findViewById(R.id.cities)
        cityAdapter = CityAdapter(cityData)
        cities.adapter = cityAdapter

    }

    private fun loadCities(){
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val cities = sharedPref.getStringSet(CITY_KEY, null)
        val gson = Gson()
        cities?.forEach{
            cityData.add(gson.fromJson(it, City::class.java))
        }
        cityData.sortBy { it.name }
    }

    private fun addCity(){
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.city_add, null)
        val countryName = dialogView.findViewById<EditText>(R.id.country_name)
        val cityName = dialogView.findViewById<EditText>(R.id.city_country)
        with(builder){
            setView(dialogView)
            setTitle("Add city")
            setPositiveButton("Add"
            ) { dialog, id ->
                val country = countryName.text.toString()
                val city = cityName.text.toString()
                if(country.isNotBlank() && city.isNotBlank()){
                    cityData.add(City(country, city))
                    cityData.sortBy { it.name }
                    saveCities()
                }
            }
            setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                }
            })

        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun saveCities(){
        val gson = Gson()
        val cities = cityData.map{gson.toJson(it)}
/*
        Json type
        "city":"New Delhi"
        "country":"India"
*/

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putStringSet(CITY_KEY, cities.toSet())
            commit()
        }
        cityAdapter.notifyDataSetChanged()
    }


    private fun resetList(){
        val builder = AlertDialog.Builder(this)
        with(builder){
            setTitle(getString(R.string.confirm_reset))
            setMessage(getString(R.string.confirm_reset_message))

            setPositiveButton(getString(R.string.yes)
            ) { dialog, which ->
                cityData.clear()
                saveCities()
            }
            setNegativeButton(getString(R.string.no), object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                }
            })
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}
