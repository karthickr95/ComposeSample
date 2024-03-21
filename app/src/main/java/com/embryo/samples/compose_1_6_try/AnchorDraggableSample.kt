package com.embryo.samples.compose_1_6_try

import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.embryo.commons.home.SampleScaffold
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnchoredDraggableSample(
    onBackClick: () -> Unit,
) {
    SampleScaffold(
        title = "Anchor Draggable",
        onBackClick = onBackClick
    ) {

        BoxWithConstraints {

            val density = LocalDensity.current
            val width = remember { with(density) { maxWidth.minus(50.dp).toPx() } }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val draggableAnchors = remember {
                    DraggableAnchors {
                        DragValue.Start at width.times(0f)
                        DragValue.Center at width.times(0.5f)
                        DragValue.End at width.times(1f)
                    }
                }

                val decayAnimationSpec = rememberSplineBasedDecay<Float>()

                val state = remember {
                    AnchoredDraggableState(
                        initialValue = DragValue.Start,
                        anchors = draggableAnchors,
                        positionalThreshold = { distance -> distance * 0.5f },
                        velocityThreshold = { with(density) { 56.dp.toPx() } },
                        snapAnimationSpec = spring(),
                        decayAnimationSpec = decayAnimationSpec
                    )
                }

                Spacer(
                    modifier = Modifier
                        .size(50.dp)
                        .offset {
                            IntOffset(
                                x = state
                                    .requireOffset()
                                    .roundToInt(),
                                y = 0,
                            )
                        }
                        .clip(CircleShape)
                        .background(Color.Red)
                        .anchoredDraggable(
                            state = state,
                            orientation = Orientation.Horizontal,
                        )
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "isAnimationRunning - ${state.isAnimationRunning}"
                    )
                    Text(
                        text = "progress - ${state.progress}"
                    )
                    Text(
                        text = "targetValue - ${state.targetValue}"
                    )
                }
            }
        }
    }
}

enum class DragValue { Start, Center, End }

@Preview
@Composable
private fun AnchoredDraggableSamplePreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        AnchoredDraggableSample(
            onBackClick = {}
        )
    }
}