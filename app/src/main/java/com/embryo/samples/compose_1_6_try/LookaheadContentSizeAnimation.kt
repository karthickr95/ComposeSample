package com.embryo.samples.compose_1_6_try

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.embryo.commons.home.SampleScaffold
import kotlinx.coroutines.launch

@Composable
fun AnimateContentSizeAfterLookaheadPass(
    onBackClick: () -> Unit,
) {
    SampleScaffold(
        title = "Lookahead ContentSize Coordinate",
        onBackClick = onBackClick
    ) {
        var sizeAnim by remember {
            mutableStateOf<Animatable<IntSize, AnimationVector2D>?>(null)
        }
        var lookaheadSize by remember {
            mutableStateOf<IntSize?>(null)
        }
        val coroutineScope = rememberCoroutineScope()
        LookaheadScope {
            // The Box is in a LookaheadScope. This means there will be a lookahead measure pass
            // before the main measure pass.
            // Here we are creating something similar to the `animateContentSize` modifier.
            Box(
                Modifier
                    .fillMaxSize()
                    .clipToBounds()
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)

                        val measuredSize = IntSize(placeable.width, placeable.height)
                        val (width, height) = if (isLookingAhead) {
                            // Record lookahead size if we are in lookahead pass. This lookahead size
                            // will be used for size animation, such that the main measure pass will
                            // gradually change size until it reaches the lookahead size.
                            lookaheadSize = measuredSize
                            measuredSize
                        } else {
                            // Since we are in an explicit lookaheadScope, we know the lookahead pass
                            // is guaranteed to happen, therefore the lookahead size that we recorded is
                            // not null.
                            val target = requireNotNull(lookaheadSize)
                            val anim = sizeAnim?.also {
                                coroutineScope.launch { it.animateTo(target) }
                            } ?: Animatable(target, IntSize.VectorConverter)
                            sizeAnim = anim
                            // By returning the animated size only during main pass, we are allowing
                            // lookahead pass to see the future layout past the animation.
                            anim.value
                        }

                        layout(width, height) {
                            placeable.place(0, 0)
                        }
                    }
            ) {
                var size by remember { mutableStateOf(false) }

                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(size = if (size) 150.dp else 50.dp)
                        .clip(CircleShape)
                        .background(Color.Red, CircleShape)
                        .clickable { size = !size }
                )
            }
        }
    }
}