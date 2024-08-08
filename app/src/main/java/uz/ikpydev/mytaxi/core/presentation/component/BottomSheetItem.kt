package uz.ikpydev.mytaxi.core.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import uz.ikpydev.mytaxi.R

@Composable
fun BottomSheetItem(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    value: String = ""
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { Unit  }
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomIcon(icon = icon)
        CustomText(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .weight(1F),
            text = title,
            fontWeight = FontWeight.SemiBold
        )
        CustomText(
            text = value,
            textColor = MaterialTheme.colorScheme.onSecondary,
            fontWeight = FontWeight.SemiBold
        )
        CustomIcon(
            modifier = Modifier.padding(start = 2.dp),
            icon = R.drawable.ic_arrow_right,
            iconTint = MaterialTheme.colorScheme.onTertiary
        )
    }
}