package com.embryo.samples.particle

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.embryo.commons.spacer.HeightSpacer
import kotlin.math.pow
import kotlin.random.Random

data class Particle(
    val color: Color,
    val startXPosition: Float,
    val startYPosition: Float,
    val maxHorizontalDisplacement: Float,
    val maxVerticalDisplacement: Float,
    val density: Density,
) {

    private val initialXDisplacement = with(density) { 10.dp.toPx() } * randomInRange(-1f, 1f)
    private val initialYDisplacement = with(density) { 10.dp.toPx() } * randomInRange(-1f, 1f)

    private val startRadius = with(density) { 2.dp.toPx() }
    private val endRadius = with(density) {
        if (randomBoolean(20)) {
            randomInRange(startRadius, 7.dp.toPx())
        } else {
            randomInRange(1.5.dp.toPx(), startRadius)
        }
    }
    var currentRadius = 0f

    private val velocity = 4 * maxVerticalDisplacement
    private val acceleration = -2 * velocity
    var currentXPosition = 0f
    var currentYPosition = 0f

    private var visibilityThresholdLow = randomInRange(0f, 0.14f)
    private var visibilityThresholdHigh = randomInRange(0f, 0.4f)
    var alpha = 0f

    fun updateProgress(progress: Float) {
        val trajectoryProgress =
            if (progress < visibilityThresholdLow || (progress > (1 - visibilityThresholdHigh))) {
                alpha = 0f; return
            } else (progress - visibilityThresholdLow).mapInRange(
                0f, 1f - visibilityThresholdHigh - visibilityThresholdLow, 0f, 1f
            )
        alpha =
            if (trajectoryProgress < 0.7f) 1f
            else (trajectoryProgress - 0.7f).mapInRange(0f, 0.3f, 1f, 0f)
        val time = trajectoryProgress.mapInRange(0f, 1f, 0f, 1.4f)
        val verticalDisplacement = time * velocity + 0.5f * acceleration * time.pow(2)
        currentYPosition = startXPosition + initialXDisplacement - verticalDisplacement
        currentXPosition =
            startYPosition + initialYDisplacement + maxHorizontalDisplacement * trajectoryProgress
        currentRadius = startRadius + (endRadius - startRadius) * trajectoryProgress
    }
}

private fun Float.mapInRange(inMin: Float, inMax: Float, outMin: Float, outMax: Float): Float {
    return outMin + (((this - inMin) / (inMax - inMin)) * (outMax - outMin))
}

private fun randomBoolean(trueProbabilityPercentage: Int) =
    Random.nextFloat() < trueProbabilityPercentage / 100f

private fun Float.randomTillZero() = this * Random.nextFloat()

private fun randomInRange(min: Float, max: Float) = min + (max - min).randomTillZero()

@Composable
fun Explosion(
    progress: Float, // TODO replace with float lambda
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        val halfSize = with(density) { maxWidth.toPx() } / 2
        val particles = remember {
            List(150) {
                Particle(
                    density = density,
                    color = Color(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255)),
                    startXPosition = halfSize,
                    startYPosition = halfSize,
                    maxHorizontalDisplacement = halfSize * randomInRange(-0.9f, 0.9f),
                    maxVerticalDisplacement = with(density) { maxWidth.toPx() } *
                            randomInRange(0.2f, 0.38f),
                )
            }
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.secondary
                )
        ) {

            // X-Axis
            drawLine(
                color = Color.Black,
                start = Offset(0f, halfSize),
                end = Offset(size.width, halfSize),
                strokeWidth = 2.dp.toPx()
            )
            // Y-Axis
            drawLine(
                color = Color.Black,
                start = Offset(halfSize, 0f),
                end = Offset(halfSize, size.width),
                strokeWidth = 2.dp.toPx()
            )

            val progressValue = progress
            particles.forEach { it.updateProgress(progressValue) }

            // Particle
            particles.forEach { particle ->
                drawCircle(
                    alpha = particle.alpha,
                    color = particle.color,
                    radius = particle.currentRadius,
                    center = Offset(
                        x = particle.currentXPosition,
                        y = particle.currentYPosition,
                    )
                )
            }
        }
    }
}

@Composable
@Preview
private fun ExplosionPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        HeightSpacer(16.dp)
        val progress = remember { mutableStateOf(0f) }
        Explosion(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally),
            progress = progress.value ,
        )
        Slider(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            value = progress.value,
            onValueChange = { progress.value = it }
        )
    }
}