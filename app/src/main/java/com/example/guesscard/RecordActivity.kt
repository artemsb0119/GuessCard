package com.example.guesscard

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

class RecordActivity : AppCompatActivity() {

    private lateinit var imageViewFon2: ImageView

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecordAdapter
    private lateinit var activity: Activity
    private lateinit var records: List<Record>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        imageViewFon2 = findViewById(R.id.imageViewFon2)
        recyclerView = findViewById(R.id.recyclerView)

        val verticalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = verticalLayoutManager
        activity = this

        Glide.with(this)
            .load("http://135.181.248.237/25/fon2.png")
            .into(imageViewFon2)

        loadData()
    }

    private fun loadData() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val data = URL("http://135.181.248.237/25/record.json").readText()
                val gson = Gson()
                records = gson.fromJson(data, Array<Record>::class.java).toList()

                runOnUiThread {
                    adapter = RecordAdapter(records)
                    recyclerView.adapter = adapter
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}