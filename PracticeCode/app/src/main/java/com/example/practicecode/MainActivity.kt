package com.example.practicecode

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.practicecode.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var tv:TextView
    lateinit var bt:Button
    lateinit var btDisable:Button
    var count =0
    lateinit var b: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

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

        val watcher = object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

        }

        b.no1.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                b.no1.setBackgroundResource(R.drawable.custom_background_2)
                val view = currentFocus
                val viewToRight = view?.focusSearch(View.FOCUS_RIGHT)
                if(!s.isNullOrEmpty())
                    viewToRight?.requestFocus()
                if(s.isNullOrEmpty()){
                    b.no1.setBackgroundResource(R.drawable.custom_background_1)
//                    val viewToLeft = view?.focusSearch(View.FOCUS_LEFT)
//                    viewToLeft?.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        b.no2.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                b.no2.setBackgroundResource(R.drawable.custom_background_2)
                val view = currentFocus
                val viewToRight = view?.focusSearch(View.FOCUS_RIGHT)
                viewToRight?.requestFocus()
                if(s.isNullOrEmpty()){
                    b.no2.setBackgroundResource(R.drawable.custom_background_1)
                    val viewToLeft = view?.focusSearch(View.FOCUS_LEFT)
                    viewToLeft?.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        b.no3.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                b.no3.setBackgroundResource(R.drawable.custom_background_2)
                val view = currentFocus
                val viewToRight = view?.focusSearch(View.FOCUS_RIGHT)
                viewToRight?.requestFocus()
                if(s.isNullOrEmpty()){
                    b.no3.setBackgroundResource(R.drawable.custom_background_1)
                    val viewToLeft = view?.focusSearch(View.FOCUS_LEFT)
                    viewToLeft?.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        b.no4.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                b.no4.setBackgroundResource(R.drawable.custom_background_2)
                val view = currentFocus
//                val viewToRight = view?.focusSearch(View.FOCUS_RIGHT)
//                viewToRight?.requestFocus()
                if(s.isNullOrEmpty()){
                    b.no4.setBackgroundResource(R.drawable.custom_background_1)
                    val viewToLeft = view?.focusSearch(View.FOCUS_LEFT)
                    viewToLeft?.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

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