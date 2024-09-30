package com.embryo.samples.animate_bounds

import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.animateBounds
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.unit.dp
import com.embryo.commons.home.SampleScaffold


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AnimateBoundsSample(
    onBackClick: () -> Unit,
) {
    SampleScaffold(
        title = "Animate Bounds",
        onBackClick = onBackClick
    ) {
        // Example where the change in content triggers the layout change on the item with animateBounds
        val textShort = remember { "Foo ".repeat(10) }
        val textLong = remember { "Bar ".repeat(50) }

        var toggle by remember { mutableStateOf(true) }

        LookaheadScope {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = if (toggle) textShort else textLong,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.7f)
                        .background(Color.LightGray)
                        .animateBounds(
                            lookaheadScope = this@LookaheadScope,
                            boundsTransform = { initialBounds, targetBounds -> spring() }
                        )
                        .clickable { toggle = !toggle }
                        .padding(10.dp),
                )
            }
        }
    }
}