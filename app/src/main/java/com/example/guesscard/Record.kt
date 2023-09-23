package com.example.guesscard

import com.google.gson.annotations.SerializedName

data class Record(
    @SerializedName("name") val name: String,
    @SerializedName("score") val score: Int
)
