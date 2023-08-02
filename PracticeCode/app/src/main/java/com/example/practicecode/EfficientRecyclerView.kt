package com.example.practicecode

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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class EfficientRecyclerView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_efficient_recycler_view)

        val countries = listOf(
            Country("pr","Puerto Rico",getString(R.string.pr_desp),R.drawable.pr,"America/Puerto_Rico"),
            Country("np","Nepal",getString(R.string.np_desp),R.drawable.np,"Asia/Katmandu"),
            Country("in","India",getString(R.string.in_desp),R.drawable.`in`,"Asia/Calcutta"),
            Country("uk","United Kingdom",getString(R.string.uk_desp), R.drawable.uk,"Europe/London"),
            Country("us","United States",getString(R.string.us_desp),R.drawable.us,"America/Los_Angeles"),
            Country("wl","Wales",getString(R.string.wl_desp),R.drawable.wl,"Australia/Broken_Hill")
        )

        val rv:RecyclerView = findViewById(R.id.eff_rv)
        with(rv){
            layoutManager = LinearLayoutManager(this@EfficientRecyclerView)

            adapter = EfficientCountryAdapter{
                Toast.makeText(this@EfficientRecyclerView,"Country was ${it.country}",Toast.LENGTH_LONG).show()
            }
            setHasFixedSize(true)

        }

        val bt:Button = findViewById(R.id.show_countries)
        bt.setOnClickListener {
            (rv.adapter as EfficientCountryAdapter).apply {
                submitList(countries.shuffled())
            }
        }
    }
}

//ListAdapter takes Datatype (which is country in out case) and ViewHolder with object of DiffCallBack in its constructor
class EfficientCountryAdapter(private val listener:(Country)->Unit): ListAdapter<Country, EfficientCountryAdapter.CViewHolder>(DiffCallBack()){
    inner class CViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val countryFlag: ImageView = view.findViewById(R.id.img)
        val countryName: TextView = view.findViewById(R.id.name)
        val description: TextView = view.findViewById(R.id.about)
        val countryClock : TextClock = view.findViewById(R.id.textClock)

        init {
            itemView.setOnClickListener {
                listener.invoke(getItem(adapterPosition))
            }
        }
        fun bind(country: Country){
            with(country){
                countryFlag.setImageResource(flagId)
                countryClock.timeZone = tz
                countryClock.format12Hour= "Ti'm'e:  hh:mm:ss a"
                countryName.text = this.country
                description.text = desp
            }
        }
    }

    class DiffCallBack: DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
            //As Country is data class == will call equals function
            //which is implemented for data classes and compares each property
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CViewHolder {
        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.country_item,parent,false)
        return CViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: CViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}