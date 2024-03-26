package com.embryo.samples.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.approachLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import com.embryo.commons.home.SampleScaffold


@Composable
fun DemoScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SampleScaffold(
        title = "Demo Screen",
        onBackClick = onBackClick,
        modifier = modifier,
    ) {
        val expanded = remember { mutableStateOf(false) }
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable { expanded.value = !expanded.value }) {

            LookaheadScope {

                val content = remember {
                    movableContentOf {
                        repeat(4) {
                            val count = remember {
                                mutableIntStateOf(0)
                            }
                            Box(modifier = Modifier
                                .animateLayout1()
                                .size(100.dp)
                                .background(attributeColors[it])
                                .clickable { count.intValue += 1 }
                            ) {
                                Text(count.intValue.toString())
                            }
                        }
                    }
                }


                if (expanded.value) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        content()
                    }
                } else {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        content()
                    }
                }

            }
        }
    }
}

context(LookaheadScope)
@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimatableApi::class)
private fun Modifier.animateLayout1(): Modifier  = composed {
    val offsetAnim = remember { DeferredTargetAnimation(IntOffset.VectorConverter) }
    val scope = rememberCoroutineScope()
    this then approachLayout(
        isMeasurementApproachComplete = { true },
        isPlacementApproachComplete = {
            offsetAnim.updateTarget(
                it.localLookaheadPositionOf(it).round(),
                scope,
                AnimationSpec,
            )
            offsetAnim.isIdle
        },
        approachMeasure = { measurable, constraints ->
            measurable.measure(constraints).run {
                layout(width, height) {
                    val (x, y) =
                        coordinates?.let { coordinates ->
                            val origin = this.lookaheadScopeCoordinates
                            val animOffset = offsetAnim.updateTarget(
                                origin.localLookaheadPositionOf(coordinates).round(),
                                scope,
                                AnimationSpec,
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
    )
}

private val AnimationSpec : FiniteAnimationSpec<IntOffset> = tween(2000)

val attributeColors = listOf(
    Color(0xFFFF928D),
    Color(0xFFFFDB8D),
    Color(0xFFA7E5FF),
    Color(0xFFB6E67F),
)

@Preview
@Composable
private fun DemoPreview() {
    DemoScreen(
        onBackClick = {}
    )
}