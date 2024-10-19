package com.example.practicaltask

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.CalendarContract.Attendees.query
import android.provider.ContactsContract.Directory
import android.provider.MediaStore
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.util.concurrent.TimeUnit


class FileViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_view)

        val path = Environment.getExternalStoragePublicDirectory("Download").toString()
        Log.d("Files", "Path: $path")

        val directory = File(path)
        val files = directory.listFiles()
        Log.d("Files", "Size: " + files.size)
        for (i in files) {
            Log.d("Files", "FileName:$i  ${i.isFile} or ${i.isDirectory}")

        }


/*
        Log.d("Files", "Path: $path")
        val directory = File(path)
        val files = directory.listFiles()
        if (directory.canRead() && files != null) {
            Log.d("Files", "Size: " + files.size)
            for (file in files) Log.d("FILE", file.name)
        } else Log.d("Files", "Null: it is null")
*/

        val rv = findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(this@FileViewActivity,RecyclerView.VERTICAL,false)

            setHasFixedSize(true)
        }

    }
}