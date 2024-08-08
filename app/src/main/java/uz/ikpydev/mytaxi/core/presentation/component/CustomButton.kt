package uz.ikpydev.mytaxi.core.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButtonWithIcon(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    containerColor: Color = MaterialTheme.colorScheme.background,
    containerInsideColor: Color = MaterialTheme.colorScheme.background,
    containerRadius: Dp = 14.dp,
    iconTint: Color = MaterialTheme.colorScheme.onPrimary,
    alpha: Float = 1f,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .size(56.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(containerRadius), clip = true)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .clip(RoundedCornerShape(size = 10.dp))
                .background(color = containerInsideColor)
                .alpha(alpha),
            contentAlignment = Alignment.Center) {
            CustomIcon(
                modifier = Modifier.alpha(alpha),
                icon = icon,
                iconTint = iconTint
            )
        }
    }
}

@Composable
fun CustomTextButton(
    modifier: Modifier = Modifier,
    text: String = "",
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    textSize: TextUnit = 18.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    containerColor: Color = MaterialTheme.colorScheme.background,
    containerInsideColor: Color = MaterialTheme.colorScheme.background,
    containerRadius: Dp = 14.dp,
    alpha: Float = 1f,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .size(56.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(containerRadius), clip = true)
            .clickable { onClick() }
            .alpha(alpha),
        colors = CardDefaults.cardColors(containerColor = containerColor),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .clip(RoundedCornerShape(size = 10.dp))
                .background(color = containerInsideColor),
            contentAlignment = Alignment.Center) {
            CustomText(
                text = text,
                textColor = textColor,
                textSize = textSize,
                fontWeight = fontWeight
            )
        }
    }
}