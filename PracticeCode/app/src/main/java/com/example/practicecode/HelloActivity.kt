package com.example.practicecode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class HelloActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        var intent:Bundle? = intent.extras

        val guestName:String? = intent?.getString("Name")
        val age:Int? = intent?.getInt("Age")
        age.let {
            val name = findViewById<TextView>(R.id.textView2)
            name.text = getString(R.string.message,guestName,it)
            Toast.makeText(this,"Activity launched $guestName who is $it",Toast.LENGTH_LONG).show()
        }


    }
}