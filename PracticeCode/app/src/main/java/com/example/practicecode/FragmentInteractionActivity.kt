package com.example.practicecode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class FragmentInteractionActivity : AppCompatActivity(),OneFragment.OnMessageClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_interaction)


        val f = OneFragment()
        //to show One fragment inside frameLayout
        supportFragmentManager.beginTransaction().add(R.id.imain_container,f,"One Fragment").commit()
        val show =  findViewById<Button>(R.id.show_fragment)
        show.setOnClickListener {
            val oneFragment = supportFragmentManager.findFragmentByTag("One Fragment") as OneFragment
            oneFragment.showFragmentMsg()
        }

    }

    fun showActivityMsg() {
        Toast.makeText(this@FragmentInteractionActivity,"This msg is from activity",Toast.LENGTH_LONG).show()
    }

    override fun onMsgClick() {
        showActivityMsg()
    }
}