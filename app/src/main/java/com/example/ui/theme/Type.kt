package com.example.ui.theme

import android.graphics.Typeface
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Arial Black font family using Android's native sans-serif-black (Black/Heavy 900 weight)
val ArialBlackFontFamily: FontFamily = FontFamily(Typeface.create("sans-serif-black", Typeface.BOLD))

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = ArialBlackFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 32.sp,
        lineHeight = 38.sp,
        color = Color.White
    ),
    displayMedium = TextStyle(
        fontFamily = ArialBlackFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        color = Color.White
    ),
    displaySmall = TextStyle(
        fontFamily = ArialBlackFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        color = Color.White
    ),
    headlineLarge = TextStyle(
        fontFamily = ArialBlackFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        color = Color.White
    ),
    headlineMedium = TextStyle(
        fontFamily = ArialBlackFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        color = Color.White
    ),
    headlineSmall = TextStyle(
        fontFamily = ArialBlackFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        color = Color.White
    ),
    titleLarge = TextStyle(
        fontFamily = ArialBlackFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        color = Color.White
    ),
    titleMedium = TextStyle(
        fontFamily = ArialBlackFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        color = Color.White
    ),
    titleSmall = TextStyle(
        fontFamily = ArialBlackFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = Color.White
    ),
    bodyLarge = TextStyle(
        fontFamily = ArialBlackFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 15.sp,
        lineHeight = 22.sp,
        color = Color.White
    ),
    bodyMedium = TextStyle(
        fontFamily = ArialBlackFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = Color.White
    ),
    bodySmall = TextStyle(
        fontFamily = ArialBlackFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        color = Color.White
    ),
    labelLarge = TextStyle(
        fontFamily = ArialBlackFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        color = Color.White
    ),
    labelMedium = TextStyle(
        fontFamily = ArialBlackFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        color = Color.White
    ),
    labelSmall = TextStyle(
        fontFamily = ArialBlackFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        color = Color.White
    )
)

