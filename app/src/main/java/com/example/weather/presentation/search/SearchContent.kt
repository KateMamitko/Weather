package com.example.weather.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weather.domain.entety.City

@Composable
fun SearchContent(searchComponent: SearchComponent) {
    val model by searchComponent.model.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "on Back",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable {
                        searchComponent.onClickBack()
                    })

            OutlinedTextField(
                value = model.searchQuery,
                onValueChange = { searchComponent.changeSearchQuery(it) },
                label = { Text("Enter city name") },
                placeholder = { Text("London") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable { searchComponent.onClickSearch() }
            )
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            when (val state = model.searchResult) {
                is SearchState.SearchResult.Complete -> {
                    items(items = state.listCity, key = { it.id }) { item ->
                        ItemCityBox(item) {
                            searchComponent.onClickToItemCity(item,searchComponent.openReason)
                        }
                    }
                }

                SearchState.SearchResult.Initial -> {}
                SearchState.SearchResult.Loading -> {}
            }
        }
    }


}

@Composable
fun ItemCityBox(item: City, onClickToItem: (City) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 7.dp)
            .clickable {
                onClickToItem(item)
            },
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            item.cityName,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
        )
        Text(
            item.country,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
        )
    }
}
