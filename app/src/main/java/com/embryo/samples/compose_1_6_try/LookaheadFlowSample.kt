package com.embryo.samples.compose_1_6_try

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.embryo.commons.home.SampleScaffold

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LookaheadFlowSample(
    onBackClick: () -> Unit,
) {
    SampleScaffold(
        title = "Lookahead Coordinate",
        onBackClick = onBackClick,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var isHorizontal by remember { mutableStateOf(true) }
        var debug by remember { mutableStateOf(true) }

        Row(
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        ) {
            Button(
                onClick = { isHorizontal = !isHorizontal }
            ) {
                Text("Toggle")
            }
            Button(
                onClick = { debug = !debug }
            ) {
                Text("Debug")
            }
        }
        val positionAnimSpec: TweenSpec<IntOffset> = remember {
            tween(2000)
        }
        Column(
            Modifier
                .background(Color(0xfffdedac), RoundedCornerShape(10))
                .padding(10.dp)
        ) {
            Text("LookaheadScope + Modifier.animateBounds")
            LookaheadScope {
                FlowRow(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.CenterStart)
                ) {
                    Box(
                        Modifier
                            .height(50.dp)
                            .animateBounds(
                                Modifier.fillMaxWidth(if (isHorizontal) 0.4f else 1f),
                                debug = debug,
                                positionAnimationSpec = positionAnimSpec,
                            )
                            .background(colors[0], RoundedCornerShape(10))
                    )
                    Box(
                        Modifier
                            .height(50.dp)
                            .animateBounds(
                                Modifier.fillMaxWidth(if (isHorizontal) 0.2f else 0.4f),
                                debug = debug,
                                positionAnimationSpec = positionAnimSpec,
                            )
                            .background(colors[1], RoundedCornerShape(10))
                    )
                    Box(
                        Modifier
                            .height(50.dp)
                            .animateBounds(
                                Modifier.fillMaxWidth(if (isHorizontal) 0.2f else 0.4f),
                                debug = debug,
                                positionAnimationSpec = positionAnimSpec,
                            )
                            .background(colors[2], RoundedCornerShape(10))
                    )
                }
                Box(Modifier.size(if (isHorizontal) 100.dp else 60.dp))
            }
        }

        Spacer(Modifier.size(50.dp))

        Column(
            Modifier
                .background(Color(0xfffdedac), RoundedCornerShape(10))
                .padding(10.dp)
        ) {
            Text("Animating Width")
            FlowRow(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.CenterStart)
            ) {
                Box(
                    Modifier
                        .height(50.dp)
                        .fillMaxWidth(animateFloatAsState(if (isHorizontal) 0.4f else 1f).value)
                        .background(colors[0], RoundedCornerShape(10))
                )
                Box(
                    Modifier
                        .height(50.dp)
                        .fillMaxWidth(animateFloatAsState(if (isHorizontal) 0.2f else 0.4f).value)
                        .background(colors[1], RoundedCornerShape(10))
                )
                Box(
                    Modifier
                        .height(50.dp)
                        .fillMaxWidth(animateFloatAsState(if (isHorizontal) 0.2f else 0.4f).value)
                        .background(colors[2], RoundedCornerShape(10))
                )
            }
        }
    }
}

private val colors = listOf(
    Color(0xffff6f69),
    Color(0xffffcc5c),
    Color(0xff2a9d84),
    Color(0xff264653)
)