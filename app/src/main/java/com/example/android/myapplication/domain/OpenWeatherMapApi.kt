package com.example.android.myapplication.domain

import com.example.android.myapplication.BuildConfig
import com.example.android.myapplication.data.CityData
import com.example.android.myapplication.data.CityWeatherData
import com.example.android.myapplication.data.CurrentWeatherData
import retrofit2.http.GET
import retrofit2.http.Query



interface OpenWeatherMapApi {
    //https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
    @GET("data/2.5/weather")
    suspend fun getCityWeatherData(@Query("q") cityName:String, @Query("appId") appId:String = BuildConfig.API_KEY): CityWeatherData
    @GET("data/2.5/weather")
    //https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
    suspend fun getCurrentWeatherData(@Query("lat") lat:Double,@Query("lon") double: Double, @Query("appId") appId:String = BuildConfig.API_KEY): CurrentWeatherData
    @GET("/geo/1.0/direct")
    //https://api.openweathermap.org/geo/1.0/direct?q={q}&limit=10000&appid={API Key}
    suspend fun getCityAutoComplete(@Query("q") q:String,@Query("limit") limit: Int = 1000,  @Query("appId") appId:String = BuildConfig.API_KEY): CityData
}