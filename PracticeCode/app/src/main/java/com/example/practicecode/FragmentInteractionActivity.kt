package com.example.practicecode

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

class FragmentInteractionActivity : AppCompatActivity(),OneFragment.OnMessageClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_interaction)

        val img = findViewById<ImageView>(R.id.image_glide)
        Glide.with(this).load("https://upload.wikimedia.org/wikipedia/commons/0/0e/Agatha_Christie_as_a_child_No_1.jpg")
            .error(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(img)

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