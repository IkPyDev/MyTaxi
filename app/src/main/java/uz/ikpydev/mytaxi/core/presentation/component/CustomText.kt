package uz.ikpydev.mytaxi.core.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import uz.ikpydev.mytaxi.feature.ui.theme.latoFontFamily

@Composable
fun CustomText(
    modifier: Modifier = Modifier,
    text: String = "",
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    textSize: TextUnit = 18.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    fontFamily: FontFamily = latoFontFamily,
    textAlign: TextAlign = TextAlign.Start,
    overFlow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = 1
) {
    Text(
        modifier = modifier,
        text = text,
        color = textColor,
        fontSize = textSize,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        textAlign = textAlign,
        overflow = overFlow,
        maxLines = maxLines,
    )
}