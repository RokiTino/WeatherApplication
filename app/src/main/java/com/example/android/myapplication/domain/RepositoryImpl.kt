package com.example.android.myapplication.domain

import com.example.android.myapplication.data.CityData
import com.example.android.myapplication.data.CityWeatherData
import com.example.android.myapplication.data.CurrentWeatherData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RepositoryImpl @Inject constructor(private val api: OpenWeatherMapApi) : Repository,CoroutineScope {
    override suspend fun getCityWeatherData(cityName: String): Flow<CityWeatherData> =
         flow {
            emit(api.getCityWeatherData(cityName))
        }.flowOn(coroutineContext)


    override suspend fun getCurrentWeatherData(lat: Double, lon: Double): Flow<CurrentWeatherData> = flow {
        emit(api.getCurrentWeatherData(lat,lon))
    }.flowOn(coroutineContext)

    override suspend fun getCityAutoComplete(cityName: String): Flow<CityData> = flow{
            emit(api.getCityAutoComplete(cityName))
        }.flowOn(coroutineContext)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
}