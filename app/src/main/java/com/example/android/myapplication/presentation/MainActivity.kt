package com.example.android.myapplication.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.android.myapplication.R
import com.example.android.myapplication.presentation.components.SearchItem
import com.example.android.myapplication.presentation.components.WetherCard
import com.example.android.myapplication.presentation.state.CityWeatherUiState
import com.example.android.myapplication.presentation.state.CurrentWeatherUiState
import com.example.android.myapplication.presentation.viewModels.WeatherViewModel
import com.example.android.myapplication.ui.theme.MyApplicationTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()
//    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        //todo
                    }
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location : Location? ->
                            location?.let { _location->
                                Log.e("Location","${_location.latitude} ${_location.longitude}")
                                viewModel.getCurrentWeatherData(_location.latitude,_location.longitude)
                            }
                        }

                } else -> {
                    Log.d("Perrmision not granted ", "Denied")
            }
            }
        }

// ...

// Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
        setContent {
            val searchText = remember{
                mutableStateOf("")
            }
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        SearchBar(
                            query = searchText.value,//text showed on SearchBar
                            onQueryChange = {userInput->
                                searchText.value = userInput
                            }, //update the value of searchText
                            onSearch = {query ->
                                viewModel.onToogleSearch(searchText.value)
                            }, //the callback to be invoked when the input service triggers the ImeAction.Search action
                            active = viewModel.isSearching.collectAsState().value, //whether the user is searching or not
                            onActiveChange = {  }, //the callback to be invoked when this search bar's active state is changed
                            placeholder = {Text("Enter a city.")},
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_search_24),
                                    contentDescription = "Search Icon"
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        /*TODO clear the field */
                                        viewModel.onToogleSearch("")
                                        viewModel.setIsSearching(false)
                                        searchText.value = ""
                                    }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clipToBounds()
                                .padding(16.dp)
                        ) {
                           LazyColumn(modifier = Modifier
                               .fillMaxWidth()
                               .wrapContentHeight()) {
                               items(viewModel.cities){item ->
                                  SearchItem(item = item, viewModel = viewModel)
                               }
                           }
                        }
                    }
                ) { innerPadding ->
                    when (val cityWeatherState = viewModel.cityWeatherUiState.collectAsState().value) {
                        is CityWeatherUiState.Error -> {
                            CenteredBox(modifier = Modifier.padding(innerPadding)) {
                                Text(text = cityWeatherState.error.message ?: "Something went wrong")
                            }
                        }
                        CityWeatherUiState.Loading -> {

                            Log.d("Waiting on Click", "Not yet Clicked")
                        }
                        is CityWeatherUiState.Success -> {
                            val cityData = cityWeatherState.data
                            val cityweather = cityData.weather[0]
                            val cityTemp = cityData.main.temp
                            val cityName = cityData.name
                            CenteredBox(modifier = Modifier.padding(top=280.dp)) {
                                Column(modifier = Modifier.fillMaxWidth()){
                                WetherCard(
                                modifier = Modifier
                                    .padding(20.dp)
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                Color(0xff20A4F3),
                                                Color(0xff182B3A)
                                            )
                                        )
                                    )
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .shadow(elevation = 5.dp),
                                weather = cityweather,
                                temp = cityTemp,
                                city = cityName
                            )
                        }
                        }}
                    }
                    when (val uiState = viewModel.currentWeatherUiState.collectAsState().value) {
                        is CurrentWeatherUiState.Error -> {
                            CenteredBox(modifier = Modifier.padding(innerPadding)) {
                                Text(text = uiState.error.message ?: "Something went wrong")
                            }
                        }
                        CurrentWeatherUiState.Loading -> {
                            CenteredBox(modifier = Modifier.padding(innerPadding)) {
                                CircularProgressIndicator()
                            }
                        }
                        is CurrentWeatherUiState.Success -> {
                            val data = uiState.data
                            val name = data.name
                            val weather  = data.weather[0]
                            val temp = data.main.temp
                            CenteredBox(modifier = Modifier.padding(innerPadding)) {
                                Column(modifier = Modifier.fillMaxWidth()){
                                    WetherCard(
                                        modifier = Modifier
                                            .padding(20.dp)
                                            .background(
                                                brush = Brush.horizontalGradient(
                                                    colors = listOf(
                                                        Color(0xff20A4F3),
                                                        Color(0xff182B3A)
                                                    )
                                                )
                                            )
                                            .fillMaxWidth()
                                            .height(100.dp)
                                            .shadow(elevation = 5.dp),
                                        weather = weather, temp = temp, city = name)


                                }

                            }

                        }

                    }
//

                }
            }
        }
    }

    }



@Composable
private fun CenteredBox(modifier: Modifier, content:@Composable () -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.Center){
        content()
    }

}