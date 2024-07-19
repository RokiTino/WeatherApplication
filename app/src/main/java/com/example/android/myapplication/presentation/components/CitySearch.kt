package com.example.android.myapplication.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.android.myapplication.R
import com.example.android.myapplication.presentation.viewModels.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitySearch(viewModel: SearchViewModel) {
    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val citiesList by viewModel.countriesList.collectAsState()

            SearchBar(
                query = searchText,//text showed on SearchBar
                onQueryChange = viewModel::onSearchTextChange, //update the value of searchText
                onSearch = viewModel::onSearchTextChange, //the callback to be invoked when the input service triggers the ImeAction.Search action
                active = isSearching, //whether the user is searching or not
                onActiveChange = { viewModel.onToogleSearch() }, //the callback to be invoked when this search bar's active state is changed
                placeholder = {Text("Enter a city.")},
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = "Search Icon"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clipToBounds()
                    .padding(16.dp)
            ) {
                LazyColumn {
                    items(citiesList) { city ->
                        Text(
                            text = city.name,
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