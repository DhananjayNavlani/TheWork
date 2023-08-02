package com.example.practicecode

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson

class DialogExample : AppCompatActivity() {
    val cityData = mutableListOf<City>()
    lateinit var cityAdapter: CityAdapter
    lateinit var addBt:Button
    lateinit var resetBt:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_example)

        addBt = findViewById<Button>(R.id.add_city)
        resetBt = findViewById<Button>(R.id.reset_city)

        addBt.setOnClickListener{
            addCity()
        }
        resetBt.setOnClickListener{
            resetList()
        }

        loadCities()
        val lv = findViewById<ListView>(R.id.list_view)
        cityAdapter = CityAdapter(cityDataL = cityData)
        lv.adapter = cityAdapter
    }

    private fun loadCities() {
        val pref = getSharedPreferences("data",Context.MODE_PRIVATE)
        val cities = pref.getStringSet("City_Key",null)
        val gson = Gson()
        cities?.forEach {
            cityData.add(gson.fromJson(it,City::class.java))
        }
        cityData.sortBy { it.country }
    }

    private fun resetList() {
        val builder = AlertDialog.Builder(this)
        with(builder){
            setTitle(getString(R.string.confirm_reset))
            setMessage(getString(R.string.confirm_reset_message))

            setPositiveButton(getString(R.string.yes)
            ) { dialog, which ->
                cityData.clear()
                saveCities()
            }

            setNegativeButton(getString(R.string.no)){dialog,which -> }
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun saveCities() {
        val gson = Gson()
        val cities = cityData.map{ gson.toJson(it)}

        val sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putStringSet("City_Key",cities.toSet())
            commit()
        }
        cityAdapter.notifyDataSetChanged()


    }

    private fun addCity() {
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView : View = inflater.inflate(R.layout.city_add,null)
        val countryName = dialogView.findViewById<EditText>(R.id.country)
        val cityName = dialogView.findViewById<EditText>(R.id.city)
        with(builder){
            setTitle("Add city")
            setView(dialogView)
            setPositiveButton("Add",object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val cntry = countryName.text.toString()
                    val city = cityName.text.toString()
                    if(cntry.isNotBlank() && city.isNotBlank()){
                        cityData.add(City(cntry,city))
                        cityData.sortBy { it.country }
                        saveCities()
                    }
                }


            })
            setNegativeButton("Cancel",object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    TODO("Not yet implemented")
                }
            })
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}

