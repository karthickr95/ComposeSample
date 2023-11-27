package com.embryo.samples.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.embryo.commons.OnClick
import kotlinx.coroutines.launch

@Composable
fun GestureDrawer(
    onBackClick: OnClick,
) {
    val drawerState = remember { mutableStateOf(DrawerValue.Closed) }

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val coroutineScope = rememberCoroutineScope()
    val drawerWidthDp by remember(configuration) {
        mutableStateOf(configuration.screenWidthDp.dp.times(0.7f))
    }
    val drawerWidthPx by remember(density) {
        mutableStateOf(with(density) { drawerWidthDp.toPx() })
    }

    val translationX = remember { Animatable(0f) }

    LaunchedEffect(translationX, drawerWidthPx) {
        translationX.updateBounds(0f, drawerWidthPx)
    }

    val draggableState = rememberDraggableState { dragAmount ->
        coroutineScope.launch {
            translationX.snapTo(translationX.value + dragAmount)
        }
    }
    val decay = rememberSplineBasedDecay<Float>()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        DrawerContent(
            modifier = Modifier
        )
        Container(
            modifier = Modifier
                .graphicsLayer {
                    this.translationX = translationX.value
                    val scale = lerp(1f, 0.8f, translationX.value / drawerWidthPx)
                    this.scaleX = scale
                    this.scaleY = scale
                }
                .draggable(
                    state = draggableState,
                    orientation = Orientation.Horizontal,
                    onDragStopped = { velocity ->
                        val decayX = decay.calculateTargetValue(
                            translationX.value,
                            velocity
                        )
                        coroutineScope.launch {
                            val targetX = if (decayX > drawerWidthPx * 0.5) drawerWidthPx else 0f
                            val canReachTargetWithDecay =
                                (decayX > targetX && targetX == drawerWidthPx)
                                        || (decayX < targetX && targetX == 0f)
                            if (canReachTargetWithDecay) {
                                translationX.animateDecay(
                                    initialVelocity = velocity,
                                    animationSpec = decay,
                                )
                            } else {
                                translationX.animateTo(targetX, initialVelocity = velocity)
                            }
                        }
                    }
                ),
            onDrawerClick = {
                drawerState.value = if (drawerState.value == DrawerValue.Closed) DrawerValue.Open
                else DrawerValue.Closed
                coroutineScope.launch {
                    if (drawerState.value == DrawerValue.Open) translationX.animateTo(drawerWidthPx)
                    else  translationX.animateTo(0f)
                }
            },
            onBackClick = onBackClick,
        )
    }
}

@Composable
private fun DrawerContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Blue)
    )
}

@Composable
private fun Container(
    modifier: Modifier = Modifier,
    onDrawerClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Green)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(onClick = onDrawerClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.List,
                    contentDescription = null,
                )
            }
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = null,
                )
            }
        }
    }
}