package com.embryo.samples.compose_1_6_try

import androidx.compose.animation.core.ExperimentalTransitionApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.embryo.commons.OnClick
import com.embryo.commons.animation.AnimatedVisibility
import com.embryo.commons.animation.rememberSeekableTransitionState
import com.embryo.samples.R
import com.embryo.samples.SampleScaffold
import kotlinx.coroutines.delay

@OptIn(ExperimentalTransitionApi::class)
@Composable
fun SeekableAnimation2(
    onBackClick: OnClick,
) {
    SampleScaffold(
        titleRes = R.string.seekable_animation_screen_2_title,
        onBackClick = onBackClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            val seek1 = rememberSeekableTransitionState(initialValue = false, targetValue = true)
            val seek2 = rememberSeekableTransitionState(initialValue = false, targetValue = true)
            val seek3 = rememberSeekableTransitionState(initialValue = false, targetValue = true)

            LaunchedEffect(Unit) {
                /*delay(500)
                seek1.animateToTargetState()
                delay(1000)
                seek2.animateToTargetState()
                delay(1000)
                seek3.animateToTargetState()*/
            }

            for (i in 1L..10L) {
                val seek = rememberSeekableTransitionState(initialValue = false, targetValue = true)

                LaunchedEffect(Unit) {
                    delay(i * 1000)
                    seek.animateToTargetState()
                }

                seek.AnimatedVisibility(
                    visible = { it },
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut(),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .background(MaterialTheme.colorScheme.tertiary, MaterialTheme.shapes.medium)
                    )
                }
            }

            seek1.AnimatedVisibility(
                visible = { it },
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut(),
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.Red)
                )
            }

            seek2.AnimatedVisibility(
                visible = { it },
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut(),
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.Red)
                )
            }

            seek3.AnimatedVisibility(
                visible = { it },
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut(),
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.Red)
                )
            }
        }
    }
}