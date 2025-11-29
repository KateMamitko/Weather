package com.example.weather.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.arkivanov.decompose.defaultComponentContext
import com.example.weather.presentation.details.DetailsViewModel
import com.example.weather.presentation.favorite.FavoriteViewModel
import com.example.weather.presentation.root.DefaultRootComponent
import com.example.weather.presentation.root.RootContent
import com.example.weather.presentation.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: DetailsViewModel by viewModels()
            val viewModel2: FavoriteViewModel by viewModels()
            val viewModel3: SearchViewModel by viewModels()

            val component = DefaultRootComponent(
                context = defaultComponentContext(),
                detailsViewModel = viewModel,
                favoriteViewModel = viewModel2,
                searchViewModel = viewModel3,
                showToast = {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }

            )
            RootContent(component)
        }
    }
}

