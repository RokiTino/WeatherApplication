package com.example.android.myapplication.presentation.state

import com.example.android.myapplication.data.CityWeatherData
import com.example.android.myapplication.data.CurrentWeatherData


sealed  interface CurrentWeatherUiState {
    data object Loading:CurrentWeatherUiState
    data class Error(val error:Throwable):CurrentWeatherUiState
    data class Success(val data: CurrentWeatherData):CurrentWeatherUiState
}

sealed interface CityWeatherUiState{
    data object Loading:CityWeatherUiState
    data class Error(val error:Throwable):CityWeatherUiState
    data class Success(val data: CityWeatherData):CityWeatherUiState
}