package com.embryo.commons.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
@NonRestartableComposable
fun HeightSpacer(
    space: Dp,
    modifier: Modifier = Modifier,
) {
    Spacer(modifier = modifier.height(space))
}

@Composable
@NonRestartableComposable
fun WidthSpacer(
    space: Dp,
    modifier: Modifier = Modifier,
) {
    Spacer(modifier = modifier.width(space))
}