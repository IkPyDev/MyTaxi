package uz.ikpydev.mytaxi.feature.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import uz.ikpydev.mytaxi.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val latoFontFamily = FontFamily(
    Font(R.font.lato_black, FontWeight.Light),
    Font(R.font.lato_reguler, FontWeight.Normal),
    Font(R.font.lato_bold, FontWeight.Bold),
)