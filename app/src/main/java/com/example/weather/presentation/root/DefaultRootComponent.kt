package com.example.weather.presentation.root

import com.arkivanov.decompose.ComponentContext

class DefaultRootComponent(val context: ComponentContext) : RootComponent,
    ComponentContext by context {

}