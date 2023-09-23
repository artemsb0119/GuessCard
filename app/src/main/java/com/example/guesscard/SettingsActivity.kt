package com.example.guesscard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

class SettingsActivity : AppCompatActivity() {

    private lateinit var imageViewFon2: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        imageViewFon2 = findViewById(R.id.imageViewFon2)
        Glide.with(this)
            .load("http://135.181.248.237/24/fon2.png")
            .into(imageViewFon2)

    }
}