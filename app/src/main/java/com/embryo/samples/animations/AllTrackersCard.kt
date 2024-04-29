package com.embryo.samples.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.*
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.embryo.commons.home.SampleScaffold

@Composable
fun AllTrackersCard(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val expanded = remember { mutableStateOf(false) }
    SampleScaffold(
        title = "All Trackers Animation",
        onBackClick = onBackClick,
        modifier = modifier,
    ) {

        LookaheadScope {

            Card(
                modifier = Modifier.trackersCardApproachSizeAnimation(),
                onClick = { expanded.value = !expanded.value },
                shape = MaterialTheme.shapes.large,
            ) {

                val movableTrackersContent = remember {
                    movableContentWithReceiverOf<LookaheadScope> {
                        for (i in 0..3) {
                            Tracker(
                                modifier = if (expanded.value) Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                else Modifier
                                    .height(50.dp)
                                    .weight(1f)
                            )
                        }
                    }
                }

                if (expanded.value) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        movableTrackersContent()
                    }
                } else {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        movableTrackersContent()
                    }
                }
            }
        }
    }
}

@Composable
private fun LookaheadScope.Tracker(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .animateTrackerBounds(this)
            .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.large)
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimatableApi::class)
private fun Modifier.trackersCardApproachSizeAnimation(): Modifier = composed {
    val sizeAnimation = remember { DeferredTargetAnimation(IntSize.VectorConverter) }
    val coroutineScope = rememberCoroutineScope()
    approachLayout(
        isMeasurementApproachInProgress = {
            sizeAnimation.updateTarget(it, coroutineScope, sizeAnimSpec)
            sizeAnimation.isIdle
        },
    ) { measurable, _ ->
        val (animWidth, animHeight) = sizeAnimation.updateTarget(lookaheadSize, coroutineScope)
        val placeable = measurable.measure(Constraints.fixed(animWidth, animHeight))
        layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
        }
    }
}

@OptIn(ExperimentalAnimatableApi::class, ExperimentalComposeUiApi::class)
private class TrackersApproachLayoutModifierNode(
    var lookaheadScope: LookaheadScope,
) : ApproachLayoutModifierNode, Modifier.Node() {

    private val sizeAnimation: DeferredTargetAnimation<IntSize, AnimationVector2D> =
        DeferredTargetAnimation(IntSize.VectorConverter)

    private val offsetAnimation: DeferredTargetAnimation<IntOffset, AnimationVector2D> =
        DeferredTargetAnimation(IntOffset.VectorConverter)

    override fun isMeasurementApproachInProgress(lookaheadSize: IntSize): Boolean {
        sizeAnimation.updateTarget(lookaheadSize, coroutineScope, sizeAnimSpec)
        return sizeAnimation.isIdle
    }

    override fun Placeable.PlacementScope.isPlacementApproachInProgress(
        lookaheadCoordinates: LayoutCoordinates,
    ): Boolean {
        val target: IntOffset = with(lookaheadScope) {
            lookaheadScopeCoordinates.localLookaheadPositionOf(lookaheadCoordinates).round()
        }
        offsetAnimation.updateTarget(target, coroutineScope, offsetAnimSpec)
        return offsetAnimation.isIdle
    }

    override fun ApproachMeasureScope.approachMeasure(
        measurable: Measurable,
        constraints: Constraints,
    ): MeasureResult {
        val (animWidth, animHeight) = sizeAnimation.updateTarget(lookaheadSize, coroutineScope)
        val placeable = measurable.measure(Constraints.fixed(animWidth, animHeight))

        return layout(placeable.width, placeable.height) {
            coordinates?.let { coordinates ->
                val target = with(lookaheadScope) {
                    lookaheadScopeCoordinates.localLookaheadPositionOf(coordinates).round()
                }
                val animatedOffset =
                    offsetAnimation.updateTarget(target, coroutineScope, offsetAnimSpec)
                val placementOffset = with(lookaheadScope) {
                    lookaheadScopeCoordinates.localPositionOf(coordinates, Offset.Zero).round()
                }
                val (x, y) = animatedOffset - placementOffset
                placeable.place(x, y)
            } ?: placeable.place(0, 0)
        }
    }
}

private data class AnimatePlacementNodeElement(val lookaheadScope: LookaheadScope) :
    ModifierNodeElement<TrackersApproachLayoutModifierNode>() {

    override fun update(node: TrackersApproachLayoutModifierNode) {
        node.lookaheadScope = lookaheadScope
    }

    override fun create(): TrackersApproachLayoutModifierNode {
        return TrackersApproachLayoutModifierNode(lookaheadScope)
    }
}

private fun Modifier.animateTrackerBounds(lookaheadScope: LookaheadScope): Modifier {
    return this.then(AnimatePlacementNodeElement(lookaheadScope))
}

private val sizeAnimSpec = tween<IntSize>(2000)
private val offsetAnimSpec = tween<IntOffset>(3000)

@Preview
@Composable
private fun AllTrackersCardPreview() {
    MaterialTheme {
        AllTrackersCard(
            modifier = Modifier.fillMaxWidth(),
            onBackClick = {},
        )
    }
}