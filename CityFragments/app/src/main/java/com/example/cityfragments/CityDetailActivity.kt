package com.example.cityfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf

class CityDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_detail)

        val cityName = intent.getStringExtra("city_name")
        Toast.makeText(this,"$cityName in activity",Toast.LENGTH_LONG).show()
        val d = DetailFragment().apply {
            arguments = bundleOf("city_name" to cityName)
        }
        supportFragmentManager.beginTransaction().add(R.id.city_detail_container,d).commit()
    }
}