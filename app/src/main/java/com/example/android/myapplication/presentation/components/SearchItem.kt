package com.example.android.myapplication.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android.myapplication.data.CityDataItem
import com.example.android.myapplication.presentation.viewModels.WeatherViewModel

@Composable
fun SearchItem(viewModel:WeatherViewModel,item: CityDataItem) {
    Box(modifier = Modifier.clickable(
        onClick = {
            viewModel.setIsSearching(false)
            viewModel.getCityWeatherData(item.name)
            
        }
    )) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .align(Alignment.Center)){
            Row (modifier=Modifier.padding(10.dp)) {
                Text(
                    text = item.name + ", ",
                    modifier = Modifier.padding(
                        start = 8.dp,
                        top = 4.dp,
                        end = 8.dp,
                        bottom = 4.dp)
                )
                Text(
                    text = item.country,
                    modifier = Modifier.padding(
                        start = 8.dp,
                        top = 4.dp,
                        end = 8.dp,
                        bottom = 4.dp)
                )
            }
        }
    }

}