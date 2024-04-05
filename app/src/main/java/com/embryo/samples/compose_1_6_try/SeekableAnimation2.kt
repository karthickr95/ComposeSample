package com.embryo.samples.compose_1_6_try

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.SeekableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.embryo.commons.OnClick
import com.embryo.commons.home.SampleScaffold
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SeekableAnimation2(
    onBackClick: OnClick,
) {
    SampleScaffold(
        title = "",
        onBackClick = onBackClick
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val transitionState1 = remember { SeekableTransitionState(false) }
            val transition1 = rememberTransition(transitionState = transitionState1)

            val transitionState2 = remember { SeekableTransitionState(false) }
            val transition2 = rememberTransition(transitionState = transitionState2)

            val transitionState3 = remember { SeekableTransitionState(false) }
            val transition3 = rememberTransition(transitionState = transitionState3)

            LaunchedEffect(Unit) {
                delay(2000)
                transitionState1.animateTo(true)
                delay(500)
                transitionState2.animateTo(true)
                delay(200)
                transitionState3.animateTo(true)
            }

            transition1.AnimatedVisibility(visible = { it }) {
                Item(Color.Red)
            }
            transition2.AnimatedVisibility(visible = { it }) {
                Item(Color.Blue)
            }
            transition3.AnimatedVisibility(visible = { it }) {
                Item(Color.Green)
            }
        }
    }
}

@Composable
private fun Item(color: Color) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color, RoundedCornerShape(16.dp))
    )
}

@Preview
@Composable
private fun SeekableAnimationPreview() {
    SeekableAnimation2(
        onBackClick = {}
    )
}