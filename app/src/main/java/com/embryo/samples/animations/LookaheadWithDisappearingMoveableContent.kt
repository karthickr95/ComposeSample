@file:OptIn(ExperimentalComposeUiApi::class)

package com.embryo.samples.animations


import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.approachLayout
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.sp
import com.embryo.commons.home.SampleScaffold
import kotlinx.coroutines.delay

@Composable
fun LookaheadWithDisappearingMovableContentDemo(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SampleScaffold(
        title = "Lookahead With Disappearing Movable Content",
        onBackClick = onBackClick,
        modifier = modifier,
    ) {
        LookaheadScope {
            val isCompact by produceState(initialValue = false) {
                while (true) {
                    delay(3000)
                    value = !value
                }
            }
            Column {

                Box(
                    Modifier
                        .padding(start = 50.dp, top = 200.dp, bottom = 100.dp)
                ) {
                    val icon = remember {
                        movableContentOf<Boolean> {
                            MyIcon(it)
                        }
                    }
                    val title = remember {
                        movableContentOf<Boolean> {
                            Title(visible = it, Modifier.animatePosition())
                        }
                    }
                    val details = remember {
                        movableContentOf<Boolean> {
                            Details(visible = it)
                        }
                    }

                    Row(
                        Modifier
                            .background(Color.Yellow)
                            .animateContentSize(), verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isCompact) {
                            icon(true)
                            Column {
                                title(true)
                                details(true)
                            }
                        } else {
                            icon(false)
                            Column {
                                title(true)
                                details(false)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyIcon(visible: Boolean, modifier: Modifier = Modifier) {
    AnimatedVisibility(
        visible,
        enter = fadeIn(),
        exit = fadeOut() + slideOutHorizontally { -it },
        modifier = modifier
    ) {
        Box(
            modifier
                .size(40.dp)
                .background(color = Color.Red, CircleShape)
        )
    }
}

@Composable
fun Title(visible: Boolean, modifier: Modifier = Modifier) {
    AnimatedVisibility(visible, enter = fadeIn(), exit = fadeOut(), modifier = modifier) {
        Text("Text", modifier, fontSize = 30.sp)
    }
}

@Composable
fun Details(visible: Boolean, modifier: Modifier = Modifier) {
    AnimatedVisibility(
        visible, enter = fadeIn(),
        exit = fadeOut() + slideOutVertically { it },
        modifier = modifier
    ) {
        Text("Detailed Text", fontSize = 18.sp)
    }
}

context(LookaheadScope)
@OptIn(ExperimentalAnimatableApi::class)
@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.animatePosition(): Modifier = composed {
    val offsetAnimation = remember {
        DeferredTargetAnimation(IntOffset.VectorConverter)
    }
    val coroutineScope = rememberCoroutineScope()
    this.approachLayout(
        isMeasurementApproachInProgress = { true },
        isPlacementApproachInProgress = {
            offsetAnimation.updateTarget(
                lookaheadScopeCoordinates.localLookaheadPositionOf(
                    it
                ).round(),
                coroutineScope,
                spring(stiffness = Spring.StiffnessMediumLow)
            )
            offsetAnimation.isIdle
        }
    ) { measurable, constraints ->
        measurable.measure(constraints).run {
            layout(width, height) {
                val (x, y) =
                    coordinates?.let { coordinates ->
                        val origin = this.lookaheadScopeCoordinates
                        val animOffset = offsetAnimation.updateTarget(
                            origin.localLookaheadPositionOf(
                                coordinates
                            ).round(),
                            coroutineScope,
                            spring(stiffness = Spring.StiffnessMediumLow),
                        )
                        val currentOffset =
                            origin.localPositionOf(
                                coordinates,
                                Offset.Zero
                            )
                        animOffset - currentOffset.round()
                    } ?: IntOffset.Zero
                place(x, y)
            }
        }
    }
}