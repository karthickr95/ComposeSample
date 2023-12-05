@file:OptIn(ExperimentalComposeUiApi::class)

package com.embryo.samples.compose_1_6_try

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.intermediateLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.embryo.commons.home.SampleScaffold
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun IntermediateLayoutSample(
    onBackClick: () -> Unit,
) {
    SampleScaffold(
        title = "Intermediate Layout",
        onBackClick = onBackClick
    ) {
        // Creates a custom modifier that animates the constraints and measures child with the
        // animated constraints. This modifier is built on top of `Modifier.intermediateLayout`, which
        // allows access to the lookahead size of the layout. A resize animation will be kicked off
        // whenever the lookahead size changes, to animate children from current size to lookahead size.
        // Fixed constraints created based on the animation value will be used to measure
        // child, so the child layout gradually changes its size and potentially its child's placement
        // to fit within the animated constraints.
        fun Modifier.animateConstraints() = composed {
            // Creates a size animation
            var sizeAnimation: Animatable<IntSize, AnimationVector2D>? by remember {
                mutableStateOf(null)
            }

            this.intermediateLayout { measurable, _ ->
                // When layout changes, the lookahead pass will calculate a new final size for the
                // child layout. This lookahead size can be used to animate the size
                // change, such that the animation starts from the current size and gradually
                // change towards `lookaheadSize`.
                if (lookaheadSize != sizeAnimation?.targetValue) {
                    sizeAnimation?.run {
                        launch { animateTo(lookaheadSize) }
                    } ?: Animatable(lookaheadSize, IntSize.VectorConverter).let {
                        sizeAnimation = it
                    }
                }
                val (width, height) = sizeAnimation!!.value
                // Creates a fixed set of constraints using the animated size
                val animatedConstraints = Constraints.fixed(width, height)
                // Measure child with animated constraints.
                val placeable = measurable.measure(animatedConstraints)
                layout(placeable.width, placeable.height) {
                    placeable.place(0, 0)
                }
            }
        }

        var fullWidth by remember { mutableStateOf(false) }
        Row(
            (if (fullWidth) Modifier.fillMaxWidth() else Modifier.width(100.dp))
                .height(200.dp)
                // Use the custom modifier created above to animate the constraints passed
                // to the child, and therefore resize children in an animation.
                .animateConstraints()
                .clickable { fullWidth = !fullWidth }) {
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.Red)
            )
            Box(
                Modifier
                    .weight(2f)
                    .fillMaxHeight()
                    .background(Color.Yellow)
            )
        }
    }
}