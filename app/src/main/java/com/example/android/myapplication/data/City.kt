package com.example.android.myapplication.data

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


data class City(
    @SerializedName("name")
    val name: String
)

interface CityApiService {
    @GET("cities")
    suspend fun searchCities(@Query("query") query: String): List<City>
}


object RetrofitInstance {
    private const val BASE_URL = "https://api.openweathermap.org/geo/1.0/direct?q=Skopje&limit=1&appid=6969392db071fab69f7a1232a959f5ba"  // Replace with your API base URL

    val api: CityApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CityApiService::class.java)
    }
}