package com.example.testcomposeapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.testcomposeapp.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val lobster = FontFamily(
    Font(R.font.lobster_regular, FontWeight.Black)
)
val roboto = FontFamily(
    Font(R.font.roboto_condensed_bold, FontWeight.Bold),
    Font(R.font.roboto_condensed_regular, FontWeight.Normal),
    Font(R.font.roboto_condensed_semibold, FontWeight.SemiBold)
)

val poppins = FontFamily(
    Font(R.font.poppins_medium, FontWeight.Normal)
)

val inter = FontFamily(
    Font(R.font.inter_18pt_regular, FontWeight.Normal),
    Font(R.font.inter_18pt_bold, FontWeight.Bold),
    Font(R.font.inter_18pt_semibold, FontWeight.SemiBold),
)