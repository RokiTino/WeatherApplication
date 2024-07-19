package com.example.android.myapplication.data

import com.google.gson.annotations.SerializedName


data class City(
    @SerializedName("name")
    val name: String
)
