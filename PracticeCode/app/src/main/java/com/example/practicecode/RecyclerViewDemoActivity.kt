package com.example.practicecode

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.lang.StringBuilder


class RecyclerViewDemoActivity : AppCompatActivity() {
    lateinit var countries:Array<Country>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_demo)

        countries = arrayOf(
            Country("pr","Puerto Rico",getString(R.string.pr_desp),R.drawable.pr,"America/Puerto_Rico"),
            Country("np","Nepal",getString(R.string.np_desp),R.drawable.np,"Asia/Katmandu"),
            Country("in","India",getString(R.string.in_desp),R.drawable.`in`,"Asia/Calcutta"),
            Country("uk","United Kingdom",getString(R.string.uk_desp), R.drawable.uk,"Europe/London"),
            Country("us","United States",getString(R.string.us_desp),R.drawable.us,"America/Los_Angeles"),
            Country("wl","Wales",getString(R.string.wl_desp),R.drawable.wl,"Australia/Broken_Hill")

        )
        val countryRv = findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(this@RecyclerViewDemoActivity,LinearLayoutManager.VERTICAL,false)
//            layoutManager = LinearLayoutManager(this@RecyclerViewDemoActivity,LinearLayoutManager.HORIZONTAL,false)
//            layoutManager = GridLayoutManager(this@RecyclerViewDemoActivity,2)
//            layoutManager = GridLayoutManager(this@RecyclerViewDemoActivity,2,GridLayoutManager.HORIZONTAL,false)
//            layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL)


/*
            //without event handling
            adapter = CountryAdapter().apply {
                setHasStableIds(true)
            }
*/
            //event handling included
            adapter = CountryAdapter{
                Toast.makeText(this@RecyclerViewDemoActivity,"Country: {${it.country}} was clicked",Toast.LENGTH_LONG).show()
            }.apply {
                setHasStableIds(true)
            }

            setHasFixedSize(true)
        }
        val showCountry = findViewById<Button>(R.id.show)
        showCountry.apply {
            setOnClickListener{
                (countryRv.adapter as CountryAdapter).countryData = countries
            }
        }
    }

}
data class Country(val code:String, val country:String, val desp:String,val flagId:Int,val tz:String = "America/Los Angeles")

class CountryAdapter(private val listener:(Country)->Unit): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(){

    var countryData = arrayOf<Country>()
        set(value){
            field = value
            notifyDataSetChanged()
        }
    inner class CountryViewHolder(view: View):RecyclerView.ViewHolder(view){
        val countryFlag: ImageView = view.findViewById(R.id.img)
        val countryName: TextView = view.findViewById(R.id.name)
        val description: TextView = view.findViewById(R.id.about)
        val countryClock : TextClock = view.findViewById(R.id.textClock)

        init{
            //itemView and adapterPosition are properties of RecyclerView class
            itemView.setOnClickListener {
                listener.invoke(countryData[adapterPosition])
            }
        }

        fun bind(countryObj: Country){
            with(countryObj){
                countryFlag.setImageResource(flagId)
                countryClock.timeZone = tz
                countryClock.format12Hour= "Ti'm'e:  hh:mm:ss a"
                countryName.text = country
                description.text = desp
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.country_item,parent,false)
        return CountryViewHolder(itemLayout)
    }

    override fun getItemCount(): Int {
        return countryData.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countryData[position])
    /*  Older code
        with(holder){
            countryFlag.setImageResource(countryData[position].flagId)
            countryClock.timeZone = countryData[position].tz
            countryName.text = countryData[position].country
            description.text = countryData[position].desp
            countryClock.format12Hour= "hh:mm:ss a"

        }
*/
    }
}