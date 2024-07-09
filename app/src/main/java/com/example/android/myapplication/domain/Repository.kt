package com.example.android.myapplication.domain

import com.example.android.myapplication.data.CityWeatherData
import com.example.android.myapplication.data.CurrentWeatherData
import kotlinx.coroutines.flow.Flow


interface Repository {
    suspend fun getCityWeatherData(cityName:String): Flow<CityWeatherData>
    suspend fun getCurrentWeatherData(lat:Double,lon:Double):Flow<CurrentWeatherData>
}