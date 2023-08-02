package com.example.practicaltask

import android.app.ActivityManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class RamUsageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ram_usage)
        // Declare and initialize TextView from the layout file
        val mTextView = findViewById<TextView>(R.id.tvRam)

        // Declaring and Initializing the ActivityManager
        val actManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager

        // Declaring MemoryInfo object
        val memInfo = ActivityManager.MemoryInfo()

        // Fetching the data from the ActivityManager
        actManager.getMemoryInfo(memInfo)

        // Fetching the available and total memory and converting into Giga Bytes
        val availMemory = memInfo.availMem.toDouble()/(1024*1024*1024)
        val totalMemory= memInfo.totalMem.toDouble()/(1024*1024*1024)

        // Displaying the memory info into the TextView
        mTextView.text = "Used Ram: ${totalMemory-availMemory}\nAvailable RAM: $availMemory\nTotal RAM: $totalMemory"

        val btn: Button = findViewById(R.id.btnRnext)
        btn.setOnClickListener {
            startActivity(Intent(this,FileViewActivity::class.java))
        }
    }
}