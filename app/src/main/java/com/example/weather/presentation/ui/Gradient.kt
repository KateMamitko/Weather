package com.example.weather.presentation.ui

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class Gradient(
    var mainColor: Brush,
    var secondColor: Brush,
    var shadowColor: Color
) {
    constructor(c1: Color, c2: Color, c3: Color, c4: Color) : this(
        mainColor = Brush.linearGradient(listOf(c1, c2)),
        secondColor = Brush.linearGradient(listOf(c3, c4)),
        shadowColor = c1
    )
}


object CardGradients {

    val gradients = listOf(
        Gradient(
            Color(0xFFBB86FC),
            Color(0xFF2F0365),
            Color(0xFFDDCAF5),
            Color(0xFF862AF6),
        ),
        Gradient(
            Color(0xFFFCD254),
            Color(0xFFFF5722),
            Color(0xFFFDB6B0),
            Color(0xFFE07F03),
        ),
        Gradient(
            Color(0xFFFABF10),
            Color(0xFF014204),
            Color(0xFF233F02),
            Color(0xFF839A6E),
        ),
        Gradient(
            Color(0xFF041FB2),
            Color(0xFFA892D0),
            Color(0xFFEFAEFA),
            Color(0xFF0321C7),
        ),
        Gradient(
            Color(0xFFE159F8),
            Color(0xFF0420B9),
            Color(0xFF03A9F4),
            Color(0xFF5C0AC0),
        ),
    )

    val themeGradient = listOf(
        Color(0xFFBCA1EE),
        Color(0xFF7D8EF3),
        Color(0xFFA378EE)
    )
}
