package uz.ikpydev.mytaxi.core.presentation.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import uz.ikpydev.mytaxi.R
import uz.ikpydev.mytaxi.core.util.DriverState
import uz.ikpydev.mytaxi.feature.ui.theme.*

fun ContentDrawScope.drawWithLayer(block: ContentDrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}

@Composable
fun CustomSwitchHeader(
    modifier: Modifier = Modifier,
    driverState: DriverState,
    onSelect: () -> Unit
) {
    val texts = stringArrayResource(id = R.array.driverState).toList()

    Card(
        modifier = modifier
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(14.dp), clip = true)
            .height(56.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
    ) {
        BoxWithConstraints(
            modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
            val maxWidth = this.maxWidth
            val tabWidth = maxWidth / texts.size

            val indicatorOffset by animateDpAsState(
                targetValue = tabWidth * driverState.isBusy,
                animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                label = "indicator offset"
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .drawWithContent {
                    drawWithLayer {
                        drawContent()
                        drawRoundRect(
                            topLeft = Offset(x = indicatorOffset.toPx(), 0f),
                            size = Size(size.width / 2, size.height),
                            color = if (driverState == DriverState.DriverBusy) ButtonRed else ButtonGreen,
                            cornerRadius = CornerRadius(
                                x = 10.dp.toPx(),
                                y = 10.dp.toPx()
                            ),
                            blendMode = BlendMode.SrcOut
                        )
                    }
                }
            ) {
                texts.forEachIndexed { index, text ->
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null,
                                onClick = {
                                    if (driverState.isBusy == index) return@clickable
                                    onSelect()
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        CustomText(
                            text = text,
                            textColor = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = if (index == driverState.isBusy) {
                                FontWeight.Bold
                            } else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}