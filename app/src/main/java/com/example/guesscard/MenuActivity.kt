package com.example.guesscard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide

class MenuActivity : AppCompatActivity() {

    private lateinit var buttonPlay: AppCompatButton
    private lateinit var buttonRecord: AppCompatButton
    private lateinit var buttonSettings: AppCompatButton
    private lateinit var buttonPlus: AppCompatButton
    private lateinit var imageViewFon2: ImageView
    private lateinit var textViewScore: TextView
    private lateinit var sharedPreferences: SharedPreferences

    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        buttonPlay = findViewById(R.id.buttonPlay)
        buttonRecord = findViewById(R.id.buttonRecord)
        buttonSettings = findViewById(R.id.buttonSettings)
        buttonPlus = findViewById(R.id.buttonPlus)
        imageViewFon2 = findViewById(R.id.imageViewFon2)
        textViewScore = findViewById(R.id.textViewScore)

        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        score = sharedPreferences.getInt("score", 5)
        textViewScore.text = score.toString()

        Glide.with(this)
            .load("http://135.181.248.237/25/fon2.png")
            .into(imageViewFon2)

        buttonPlay.setOnClickListener {
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
            finish()
        }
        buttonRecord.setOnClickListener {
            val intent = Intent(this, RecordActivity::class.java)
            startActivity(intent)
        }
        buttonSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        buttonPlus.setOnClickListener {
            if (score == 0) {
                score = 5
                textViewScore.text = score.toString()
                sharedPreferences.edit().putInt("score", score).apply()
            }
        }
    }
}