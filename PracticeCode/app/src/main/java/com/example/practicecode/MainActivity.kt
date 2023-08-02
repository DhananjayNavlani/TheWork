package com.example.practicecode

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var tv:TextView
    lateinit var bt:Button
    lateinit var btDisable:Button
    var count =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Event Listeners can detect a UI interaction between the
        //user and the application such as button clicks. Design a
        //simple application to count the number of such button
        //click events encountered within the application to
        //display them in a TextView.
        tv = findViewById(R.id.textView)
        bt = findViewById(R.id.button)
        btDisable = findViewById(R.id.button2)

        //Implicit Intent
        val launchUrl = findViewById<Button>(R.id.button3)
        launchUrl.setOnClickListener{
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("https://www.reddit.com")
                title = "Choose options from"
            }

            //checks if system has app component to support the action requested
            if(intent.resolveActivity(packageManager) != null){
                startActivity(intent)
            }else{
                Toast.makeText(this,"No activity found to handle the intent", Toast.LENGTH_LONG).show()
            }
        }

        //Explicit Intent
        val goToActivity = findViewById<Button>(R.id.button4)
        goToActivity.setOnClickListener {
            val intent = Intent(this,HelloActivity::class.java).apply {
                putExtra("Name","John")
                putExtra("Age",22)
            }
            startActivity(intent)
        }

    }
    public fun clicked(view: View){
        count++
        tv.text = "Button clicked $count times"
    }
    public fun ondisable(view: View) {
        if(bt.isEnabled) btDisable.text ="Enable"
        else btDisable.text = "Disable"
        bt.isEnabled = !bt.isEnabled
    }
}