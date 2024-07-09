package com.example.android.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.myapplication.domain.Repository
import com.example.android.myapplication.presentation.state.CityWeatherUiState
import com.example.android.myapplication.presentation.state.CurrentWeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    private val _currentWeatherUiState:MutableStateFlow<CurrentWeatherUiState> = MutableStateFlow(CurrentWeatherUiState.Loading)
    private val _cityWeatherUiState:MutableStateFlow<CityWeatherUiState> = MutableStateFlow(CityWeatherUiState.Loading)
    val currentWeatherUiState:StateFlow<CurrentWeatherUiState> = _currentWeatherUiState.asStateFlow()
    val cityWeatherUiState:StateFlow<CityWeatherUiState> = _cityWeatherUiState.asStateFlow()


    fun getCityWeatherData(cityName:String){
        viewModelScope.launch {
            repository.getCityWeatherData(cityName).catch { e ->
                _cityWeatherUiState.value = CityWeatherUiState.Error(e)
            }.onStart {
                _cityWeatherUiState.value = CityWeatherUiState.Loading
            }.collectLatest { data ->
                _cityWeatherUiState.value = CityWeatherUiState.Success(data)
            }
        }
    }
   fun getCurrentWeatherData(lat:Double,lon:Double){
       viewModelScope.launch {
           repository.getCurrentWeatherData(lat,lon).catch { e ->
               _currentWeatherUiState.value = CurrentWeatherUiState.Error(e)
           }.onStart {
               _currentWeatherUiState.value = CurrentWeatherUiState.Loading
           }.collectLatest { data ->
               _currentWeatherUiState.value = CurrentWeatherUiState.Success(data)
           }
       }
   }

}