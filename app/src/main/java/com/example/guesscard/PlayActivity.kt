package com.example.guesscard

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.KeyEvent
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide

class PlayActivity : AppCompatActivity() {

    private lateinit var imageViewFon2: ImageView
    private lateinit var rootView: ImageView
    private lateinit var imageArrow: ImageView
    private lateinit var imageCardBack: ImageView
    private lateinit var imageCardFront: ImageView
    private lateinit var imageBankir: ImageView
    private lateinit var textViewScore: TextView
    private lateinit var textViewStavka: TextView
    private lateinit var constrPik: ConstraintLayout
    private lateinit var constrKre: ConstraintLayout
    private lateinit var constrBub: ConstraintLayout
    private lateinit var constrChe: ConstraintLayout
    private lateinit var sharedPreferences: SharedPreferences

    var score = 0
    var stavka = 0
    var masty = 0
    var current = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        imageViewFon2 = findViewById(R.id.imageViewFon2)
        rootView = findViewById(R.id.rootView)
        imageArrow = findViewById(R.id.imageArrow)
        imageCardBack = findViewById(R.id.imageCardBack)
        imageCardFront = findViewById(R.id.imageCardFront)
        imageBankir = findViewById(R.id.imageBankir)
        textViewScore = findViewById(R.id.textViewScore)
        textViewStavka = findViewById(R.id.textViewStavka)
        constrPik = findViewById(R.id.constrPik)
        constrKre = findViewById(R.id.constrKre)
        constrBub = findViewById(R.id.constrBub)
        constrChe = findViewById(R.id.constrChe)

        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        score = sharedPreferences.getInt("score", 5)
        textViewScore.text = "Score: " + score.toString()

        Glide.with(this)
            .load("http://135.181.248.237/25/fon2.png")
            .into(imageViewFon2)
        Glide.with(this)
            .load("http://135.181.248.237/25/rubashka.png")
            .into(imageCardBack)
        Glide.with(this)
            .load("http://135.181.248.237/25/rubashka.png")
            .into(imageCardFront)

        imageArrow.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }

        showDialog()

        constrChe.setOnClickListener{
            constrBub.isClickable = false
            constrChe.isClickable = false
            constrKre.isClickable = false
            constrPik.isClickable = false
            current = 1
            performCardAnimation(imageCardBack, getRandomCardImageUrl(), imageBankir)
        }
        constrBub.setOnClickListener{
            constrBub.isClickable = false
            constrChe.isClickable = false
            constrKre.isClickable = false
            constrPik.isClickable = false
            current = 2
            performCardAnimation(imageCardBack, getRandomCardImageUrl(), imageBankir)
        }
        constrKre.setOnClickListener{
            constrBub.isClickable = false
            constrChe.isClickable = false
            constrKre.isClickable = false
            constrPik.isClickable = false
            current = 3
            performCardAnimation(imageCardBack, getRandomCardImageUrl(), imageBankir)
        }
        constrPik.setOnClickListener{
            constrBub.isClickable = false
            constrChe.isClickable = false
            constrKre.isClickable = false
            constrPik.isClickable = false
            current = 4
            performCardAnimation(imageCardBack, getRandomCardImageUrl(), imageBankir)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
        return super.onKeyDown(keyCode, event)
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.stavka_layout)

        val editTextValue = dialog.findViewById<TextView>(R.id.editTextValue)
        val buttonOk = dialog.findViewById<TextView>(R.id.buttonOk)

        buttonOk.setOnClickListener {
            if (TextUtils.isEmpty(editTextValue.text)) {
                stavka = 0
            } else {
                stavka = editTextValue.text.toString().toInt()
            }
            if (stavka == 0) {

            } else if (stavka <= 50 && stavka > score) {
                stavka = score
                dialog.dismiss()
            } else if (stavka > 50 && stavka <= score) {
                stavka = 50
                dialog.dismiss()
            } else if (stavka > 50 && stavka > score) {
                if (score > 50) {
                    stavka = 50
                    dialog.dismiss()
                } else {
                    stavka = score
                    dialog.dismiss()
                }
            } else {
                dialog.dismiss()
            }
            textViewStavka.text = stavka.toString()
            score -= stavka
            textViewScore.text = "Score: " + score.toString()
        }

        val window = dialog.window
        val layoutParams = WindowManager.LayoutParams()

        layoutParams.gravity = Gravity.CENTER
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = (resources.displayMetrics.heightPixels * 0.4).toInt()
        window?.attributes = layoutParams

        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    fun getRandomCardImageUrl(): String {
        val imageUrls = arrayOf(
            "http://135.181.248.237/24/_6_of_clubs.png",
            "http://135.181.248.237/24/_6_of_diamonds.png",
            "http://135.181.248.237/24/_6_of_hearts.png",
            "http://135.181.248.237/24/_6_of_spades.png",
            "http://135.181.248.237/24/_7_of_clubs.png",
            "http://135.181.248.237/24/_7_of_diamonds.png",
            "http://135.181.248.237/24/_7_of_hearts.png",
            "http://135.181.248.237/24/_7_of_spades.png",
            "http://135.181.248.237/24/_8_of_clubs.png",
            "http://135.181.248.237/24/_8_of_diamonds.png",
            "http://135.181.248.237/24/_8_of_hearts.png",
            "http://135.181.248.237/24/_8_of_spades.png",
            "http://135.181.248.237/24/_9_of_clubs.png",
            "http://135.181.248.237/24/_9_of_diamonds.png",
            "http://135.181.248.237/24/_9_of_hearts.png",
            "http://135.181.248.237/24/_9_of_spades.png",
            "http://135.181.248.237/24/_10_of_clubs.png",
            "http://135.181.248.237/24/_10_of_diamonds.png",
            "http://135.181.248.237/24/_10_of_hearts.png",
            "http://135.181.248.237/24/_10_of_spades.png",
            "http://135.181.248.237/24/jack_of_clubs2.png",
            "http://135.181.248.237/24/jack_of_diamonds2.png",
            "http://135.181.248.237/24/jack_of_hearts2.png",
            "http://135.181.248.237/24/jack_of_spades2.png",
            "http://135.181.248.237/24/queen_of_clubs2.png",
            "http://135.181.248.237/24/queen_of_diamonds2.png",
            "http://135.181.248.237/24/queen_of_hearts2.png",
            "http://135.181.248.237/24/queen_of_spades2.png",
            "http://135.181.248.237/24/king_of_clubs2.png",
            "http://135.181.248.237/24/king_of_diamonds2.png",
            "http://135.181.248.237/24/king_of_hearts2.png",
            "http://135.181.248.237/24/king_of_spades2.png",
            "http://135.181.248.237/24/ace_of_clubs.png",
            "http://135.181.248.237/24/ace_of_diamonds.png",
            "http://135.181.248.237/24/ace_of_hearts.png",
            "http://135.181.248.237/24/ace_of_spades2.png"
        )

        val randomIndex = (0 until imageUrls.size).random()
        masty = when {
            imageUrls[randomIndex].contains("hearts", ignoreCase = true) -> 1
            imageUrls[randomIndex].contains("diamonds", ignoreCase = true) -> 2
            imageUrls[randomIndex].contains("clubs", ignoreCase = true) -> 3
            imageUrls[randomIndex].contains("spades", ignoreCase = true) -> 4
            else -> 0
        }
        return imageUrls[randomIndex]
    }

    fun performCardAnimation(
        imageView: ImageView,
        targetImageUri: String,
        userImage: ImageView
    ) {
        val imageCardLocation = IntArray(2)
        val imageUserLocation = IntArray(2)

        imageView.getLocationOnScreen(imageCardLocation)
        userImage.getLocationOnScreen(imageUserLocation)

        val deltaX = imageUserLocation[0] - imageCardLocation[0]
        val deltaY = imageUserLocation[1] - imageCardLocation[1]

        val translationX = ObjectAnimator.ofFloat(imageView, "translationX", deltaX.toFloat())
        val translationY = ObjectAnimator.ofFloat(imageView, "translationY", deltaY.toFloat())
        val moveCard = AnimatorSet()
        moveCard.playTogether(translationX, translationY)
        moveCard.duration = 3000

        val rotateCard = ObjectAnimator.ofFloat(imageView, "rotationY", 0f, 180f)
        rotateCard.duration = 500
        rotateCard.startDelay = 1000

        val animators = AnimatorSet()
        animators.playSequentially(moveCard, rotateCard)

        val flashAnimationGood = AnimationUtils.loadAnimation(this, R.anim.flash_animation)
        val flashAnimationBad = AnimationUtils.loadAnimation(this, R.anim.flash_animation)

        flashAnimationGood.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                rootView.setBackgroundColor(getColor(R.color.green))
            }

            override fun onAnimationEnd(animation: Animation?) {
                rootView.setBackgroundColor(getColor(R.color.transp))
                showDialogResult()
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })

        flashAnimationBad.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                rootView.setBackgroundColor(getColor(R.color.red))
            }

            override fun onAnimationEnd(animation: Animation?) {
                rootView.setBackgroundColor(getColor(R.color.transp))
                showDialogResult()
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })

        animators.addListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(p0: Animator) {
            }

            override fun onAnimationEnd(p0: Animator) {

                Glide.with(imageView.context)
                    .load(targetImageUri)
                    .into(userImage)

                imageView.translationX = 0f
                imageView.translationY = 0f
                imageView.rotationY = 0f

                if (current == masty){
                    rootView.startAnimation(flashAnimationGood)
                    score += stavka * 4
                    sharedPreferences.edit().putInt("score", score).apply()
                    textViewScore.text = "Score: " + score.toString()
                } else {
                    rootView.startAnimation(flashAnimationBad)
                    sharedPreferences.edit().putInt("score", score).apply()
                    textViewScore.text = "Score: " + score.toString()
                }
            }

            override fun onAnimationCancel(p0: Animator) {
            }

            override fun onAnimationRepeat(p0: Animator) {
            }
        })

        animators.start()
    }

    private fun showDialogResult() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.result_layout)

        val textViewResult = dialog.findViewById<TextView>(R.id.textViewResult)
        val buttonRetry = dialog.findViewById<AppCompatButton>(R.id.buttonRetry)

        if (current == masty) {
            textViewResult.text = "YOU WON!"
        } else {
            textViewResult.text = "YOU'VE LOST!"
        }

        buttonRetry.setOnClickListener {
            recreate()
        }

        val window = dialog.window
        val layoutParams = WindowManager.LayoutParams()

        layoutParams.gravity = Gravity.CENTER
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = (resources.displayMetrics.heightPixels * 0.4).toInt()
        window?.attributes = layoutParams

        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}