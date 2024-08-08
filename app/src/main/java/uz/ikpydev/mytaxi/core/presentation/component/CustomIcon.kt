package uz.ikpydev.mytaxi.core.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CustomIcon(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    iconTint: Color = MaterialTheme.colorScheme.onSecondary
) {
    Icon(
        modifier = modifier.size(24.dp),
        painter = painterResource(id = icon),
        tint = iconTint,
        contentDescription = ""
    )
}