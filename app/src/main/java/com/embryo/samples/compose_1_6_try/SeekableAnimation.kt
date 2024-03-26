package com.embryo.samples.compose_1_6_try

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.embryo.commons.OnClick
import com.embryo.commons.home.SampleScaffold
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalTransitionApi::class, ExperimentalAnimationApi::class)
@Composable
fun SeekableAnimation(
    onBackClick: OnClick,
) {
    SampleScaffold(
        title = "Seekable Animation",
        onBackClick = onBackClick
    ) {
        val seekingState =
            remember { SeekableTransitionState(SquareSize.Small) }
        val scope = rememberCoroutineScope()

        LaunchedEffect(key1 = Unit) {
            delay(2000)
            seekingState.animateTo(SquareSize.Large)
        }
        Row {
            Button(onClick = {
                scope.launch {
                    seekingState.animateTo(SquareSize.Small)
                }
            }) {
                Text("Small")
            }
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
            )
            Button(onClick = {
                scope.launch {
                    seekingState.animateTo(SquareSize.Large)
                }
            }) {
                Text("Large")
            }
        }
        val transition = rememberTransition(seekingState)

        // Defines a float animation as a child animation the transition. The current animation value
        // can be read from the returned State<Float>.
        val scale: Float by transition.animateFloat(
            // Defines a transition spec that uses the same low-stiffness spring for *all*
            // transitions of this float, no matter what the target is.
            transitionSpec = { tween(easing = LinearEasing) },
            label = "Scale"
        ) { state ->
            // This code block declares a mapping from state to value.
            if (state == SquareSize.Large) 3f else 1f
        }

        transition.AnimatedVisibility(
            visible = { it == SquareSize.Large },
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut(),
        ) {
            Item(onClick = { /*TODO*/ })
        }

        Text(text = seekingState.currentState.name)

        transition.AnimatedContent(
            transitionSpec = {
                fadeIn(tween(easing = LinearEasing)) togetherWith fadeOut(tween(easing = LinearEasing))
            }
        ) { state ->
            if (state == SquareSize.Large) {
                Box(
                    Modifier
                        .size(50.dp)
                        .background(Color.Magenta)
                )
            } else {
                Box(
                    Modifier
                        .size(50.dp)
                        .background(Color.Green)
                )
            }
        }
        Box(
            Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .size((100 * scale).dp)
                .background(Color.Blue)
        )
    }
}

@Composable
private fun Item(
    onClick: OnClick,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(MaterialTheme.shapes.large)
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.primary)
    )
}

private enum class SquareSize { Small, Large }

@Preview
@Composable
private fun SeekableAnimationPreview() {
    MaterialTheme {
        SeekableAnimation(
            onBackClick = {}
        )
    }
}

@Preview
@Composable
private fun ItemPreview() {
    MaterialTheme {
        Item(
            onClick = {}
        )
    }
}