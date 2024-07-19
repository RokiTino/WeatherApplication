package com.example.android.myapplication.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.android.myapplication.presentation.components.CitySearch
import com.example.android.myapplication.presentation.components.WetherCard
import com.example.android.myapplication.presentation.state.CityWeatherUiState
import com.example.android.myapplication.presentation.viewModels.SearchViewModel
import com.example.android.myapplication.presentation.viewModels.WeatherViewModel
import com.example.android.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    when(val uiState = viewModel.cityWeatherUiState.collectAsState().value){
                        is CityWeatherUiState.Error ->{
                            CenteredBox(modifier = Modifier.padding(innerPadding)) {
                                Text(text = uiState.error.message ?: "Something went wrong")
                            }
                        }
                            CityWeatherUiState.Loading -> {
                                CenteredBox(modifier = Modifier.padding(innerPadding)) {
                                    CircularProgressIndicator()
                                }
                            }
                        is CityWeatherUiState.Success -> {
                            val data = uiState.data
                            val name = data.name
                            val weather  = data.weather[0]
                            val temp = data.main.temp


                            CenteredBox(modifier = Modifier.padding(innerPadding)) {
                                Column(modifier = Modifier.fillMaxWidth()){

                                    CitySearch(viewModel = searchViewModel)

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
                                        weather = weather,
                                        temp = temp,
                                        city = name
                                    )
                                }

                            }

                        }
                    }

                }
            }
        }
    }
    override fun onResume() {
        super.onResume()

        viewModel.getCityWeatherData(searchViewModel.searchText.value)
    }
}


@Composable
private fun CenteredBox(modifier: Modifier, content:@Composable () -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.Center){
        content()
    }

}