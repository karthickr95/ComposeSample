package com.embryo.samples.animations

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.embryo.samples.R
import com.embryo.samples.SampleScaffold

@Composable
fun BouncyRopes(
    onBackClick: () -> Unit
) {
    SampleScaffold(
        titleRes = R.string.bouncy_rope_title,
        onBackClick = onBackClick,
    ) {
        val startCoOrdinate by remember {
            mutableStateOf(Offset(0f, 0f))
        }
        var endCoOrdinate by remember {
            mutableStateOf(Offset(100f, 0f))
        }
        val midPoint by remember {
            derivedStateOf {
                val distance = (endCoOrdinate.x - startCoOrdinate.x)
                Offset(
                    (endCoOrdinate.x - startCoOrdinate.x) / 2f,
                    endCoOrdinate.y + distance
                )
            }
        }
        val path = remember {
            Path()
        }
        val midPointAnimated = animateOffsetAsState(
            midPoint,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )

        Canvas(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput("drag") {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    endCoOrdinate += dragAmount
                }
            },
            onDraw = {
                path.reset()
                path.moveTo(startCoOrdinate.x, startCoOrdinate.y)
                path.quadraticBezierTo(
                    midPointAnimated.value.x,
                    midPointAnimated.value.y,
                    endCoOrdinate.x,
                    endCoOrdinate.y
                )

                drawPath(path, Color.Black, style = Stroke(4.dp.toPx()))
                val radius = 10.dp.toPx()
                drawCircle(Color.Black, center = startCoOrdinate, radius = radius)
                drawCircle(Color.Black, center = endCoOrdinate, radius = radius)
            })
    }
}

@Preview
@Composable
private fun BouncyRopesPreview() {
    BouncyRopes(
        onBackClick = {}
    )
}