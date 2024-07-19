package com.example.android.myapplication.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.myapplication.data.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(

) : ViewModel() {
    //first state whether the search is happening or not
    val cities :List<City>  = mutableListOf()
    private val _citiesList = MutableStateFlow(cities)

    private val _isSearching = MutableStateFlow(false)
    private val _searchText = MutableStateFlow("Skopje")
    val isSearching = _isSearching.asStateFlow()
    //second state the text typed by the user
    val searchText = _searchText.asStateFlow()

    val countriesList = searchText
        .combine(_citiesList) { text, cities ->//combine searchText with _contriesList
            if (text.isBlank()) { //return the entery list of countries if not is typed
                cities
            }
            cities.filter { city ->// filter and return a list of countries based on the text the user typed
                city.name.uppercase().contains(text.trim().uppercase())
            }
        }.stateIn(//basically convert the Flow returned from combine operator to StateFlow
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),//it will allow the StateFlow survive 5 seconds before it been canceled
            initialValue = _citiesList.value
        )
    fun onSearchTextChange(text: String) {
        _searchText.value = text

    }

    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange(searchText.value)
        }else {
            _isSearching.value = false
            onSearchTextChange("")
        }
    }
}