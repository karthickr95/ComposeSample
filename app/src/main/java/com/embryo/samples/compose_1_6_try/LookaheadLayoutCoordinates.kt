package com.embryo.samples.compose_1_6_try

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.intermediateLayout
import androidx.compose.ui.unit.*
import com.embryo.commons.home.SampleScaffold
import kotlinx.coroutines.launch

@Composable
fun LookaheadLayoutCoordinatesSample(
    onBackClick: () -> Unit,
) {
    SampleScaffold(
        title = "Lookahead Coordinate",
        onBackClick = onBackClick
    ) {

        val colors = listOf(
            Color(0xffff6f69), Color(0xffffcc5c), Color(0xff264653), Color(0xff2a9d84)
        )

        var isInColumn by remember { mutableStateOf(true) }
        LookaheadScope {
            // Creates movable content containing 4 boxes. They will be put either in a [Row] or in a
            // [Column] depending on the state
            val items = remember {
                movableContentOf {
                    colors.forEach { color ->
                        Row {
                            Box(
                                Modifier
                                    .padding(15.dp)
                                    .size(100.dp, 80.dp)
                                    .animatePlacementInScope(this@LookaheadScope)
                                    .background(color, RoundedCornerShape(20))
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.LightGray.copy(alpha = 0.5f))
                    .animateSize(this)
                    .clickable { isInColumn = !isInColumn }
            ) {
                // As the items get moved between Column and Row, their positions in LookaheadScope
                // will change. The `animatePlacementInScope` modifier created above will
                // observe that final position change via `localLookaheadPositionOf`, and create
                // a position animation.
                if (isInColumn) {
                    Column { items() }
                } else {
                    Row { items() }
                }
            }
        }
    }
}

// Creates a custom modifier to animate the local position of the layout within the
// given LookaheadScope, whenever the relative position changes.
@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.animatePlacementInScope(lookaheadScope: LookaheadScope) = composed {
    // Creates an offset animation
    var offsetAnimation: Animatable<IntOffset, AnimationVector2D>? by
    remember { mutableStateOf(null) }

    var targetOffset: IntOffset? by remember { mutableStateOf(null) }

    this.intermediateLayout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.width, placeable.height) {
            // Converts coordinates of the current layout to LookaheadCoordinates
            val coordinates = coordinates
            if (coordinates != null) {
                // Calculates the target offset within the lookaheadScope
                val target = with(lookaheadScope) {
                    lookaheadScopeCoordinates
                        .localLookaheadPositionOf(coordinates)
                        .round().also { targetOffset = it }
                }

                // Uses the target offset to start an offset animation
                if (target != offsetAnimation?.targetValue) {
                    offsetAnimation?.run {
                        launch { animateTo(target, tween(1000)) }
                    } ?: Animatable(target, IntOffset.VectorConverter).let {
                        offsetAnimation = it
                    }
                }
                // Calculates the *current* offset within the given LookaheadScope
                val placementOffset =
                    lookaheadScopeCoordinates
                        .localPositionOf(coordinates, Offset.Zero)
                        .round()
                // Calculates the delta between animated position in scope and current
                // position in scope, and places the child at the delta offset. This puts
                // the child layout at the animated position.
                val (x, y) = requireNotNull(offsetAnimation).run { value - placementOffset }
                placeable.place(x, y)
            } else {
                placeable.place(0, 0)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.animateSize(lookaheadScope: LookaheadScope) = composed {

    var sizeAnimation: Animatable<IntSize, AnimationVector2D>? by remember {
        mutableStateOf(null)
    }

    var targetSize: IntSize? by remember { mutableStateOf(null) }

    this.intermediateLayout { measurable, constraints ->
        targetSize = lookaheadSize

        if (lookaheadSize != sizeAnimation?.targetValue) {
            sizeAnimation?.run {
                launch { animateTo(lookaheadSize) }
            } ?: Animatable(lookaheadSize, IntSize.VectorConverter).let {
                sizeAnimation = it
            }
        }
        val (width, height) = sizeAnimation?.value ?: lookaheadSize
        val placeable = measurable.measure(
            Constraints.fixed(lookaheadSize.width, lookaheadSize.height)
        )
        // Make sure the content is aligned to topStart
        val wrapperWidth = width.coerceIn(constraints.minWidth, constraints.maxWidth)
        val wrapperHeight =
            height.coerceIn(constraints.minHeight, constraints.maxHeight)
        layout(width, height) {
            placeable.place(-(wrapperWidth - width) / 2, -(wrapperHeight - height) / 2)
        }
    }
}
