package com.embryo.samples.small_animations.items

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.unit.dp

@Composable
fun ClickSparkle(
    interactionSource: InteractionSource,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {

    Box(
        modifier = modifier
            .requiredSize(48.dp)
            .drawWithContent {
                drawContent()


            },
        content = content,
    )
}