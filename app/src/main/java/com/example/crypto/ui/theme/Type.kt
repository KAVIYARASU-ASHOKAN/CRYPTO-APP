package com.iyke.crypto_tracker.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.crypto.R

// Set of Material typography styles to start with


private val RobotoBlack = FontFamily(Font(R.font.roboto_black))
val RobotoBold = FontFamily(Font(R.font.roboto_bold))
val RobotoRegular = FontFamily(Font(R.font.roboto_regular))

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = RobotoBlack,
        fontSize = 30.sp,
        lineHeight = 36.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = RobotoBold,
        fontSize = 18.sp,
        lineHeight = 30.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = RobotoBold,
        fontSize = 14.sp,
        lineHeight = 22.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.main)),
        fontSize = 30.sp,
        lineHeight = 36.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.sub)),
        fontSize = 22.sp,
        lineHeight = 30.sp
    ),
//    = TextStyle(
//        fontFamily = RobotoRegular,
//        fontSize = 12.sp,
//        lineHeight = 22.sp
//    ), // Subtitle 1 is body 3
//    subtitle2 = TextStyle(
//        fontFamily = RobotoRegular,
//        fontSize = 14.sp,
//        lineHeight = 22.sp
//    ), // Subtitle 2 is body 4
//    h5 = TextStyle(
//        fontFamily = RobotoRegular,
//        fontSize = 10.sp,
//        lineHeight = 22.sp
//    ), // h5 is body 5
)