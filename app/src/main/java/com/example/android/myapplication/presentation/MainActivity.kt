package com.example.android.myapplication.presentation

import CitySearch
import CityViewModel
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
import com.example.android.myapplication.presentation.components.WetherCard
import com.example.android.myapplication.presentation.state.CityWeatherUiState
import com.example.android.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel:WeatherViewModel by viewModels()
    private val cityViewModel: CityViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
//                                Text(text = "Current temp in $name is $weather")
                                Column(modifier = Modifier.fillMaxWidth()){
//                                    OutlinedTextField(
//                                        modifier = Modifier.padding(20.dp),
//                                        shape = RoundedCornerShape(50),
//                                        value = data.name,
//                                        onValueChange = {
//                                            onResume(data.name)
//                                        },
//                                        placeholder = {
//                                            Text(
//                                                text = "Enter a city"
//                                            )
//                                        },
//                                        leadingIcon = {
//                                            IconButton(onClick = { }) {
//                                                Icon(
//                                                    imageVector = Icons.Default.Search,
//                                                    contentDescription = null,
//                                                    tint = Color.Gray,
//                                                    modifier = Modifier.size(22.dp)
//                                                )
//                                            }
//                                        },
//                                    )

                                    CitySearch(cityViewModel)
                                    WetherCard(
                                        modifier = Modifier
                                            .padding(30.dp)
                                            .background(
                                                brush = Brush.horizontalGradient(
                                                    colors = listOf(
                                                        Color(0xff20A4F3),
                                                        Color(0xff182B3A)
                                                    )
                                                )
                                            )
                                            .fillMaxWidth()
                                            .height(150.dp)
                                            .shadow(elevation = 3.dp),
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
        viewModel.getCityWeatherData(cityName = cityViewModel.q)
    }
}



@Composable
private fun CenteredBox(modifier: Modifier, content:@Composable () -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.Center){
        content()
    }

}
//
//@Composable
//private fun SearchField(viewModel: SearchViewModel) {
//    OutlinedTextField(
//        modifier = Modifier.fillMaxWidth(),
//        value = viewModel.searchFieldValue,
//        onValueChange = { viewModel.updateSearchField(it) },
//        label = {
//            Text(text = "Enter a city")
//        },
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//        maxLines = 1,
//        trailingIcon = {
//            IconButton(onClick = { viewModel.searchCityClick() }) {
//                Icon(
//                    painter = painterResource(id = R.drawable.baseline_search_24),
//                    contentDescription = null
//                )
//            }
//        }
//    )
//}