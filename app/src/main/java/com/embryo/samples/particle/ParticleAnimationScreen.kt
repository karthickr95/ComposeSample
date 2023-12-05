package com.embryo.samples.particle

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.embryo.commons.OnClick
import com.embryo.commons.spacer.HeightSpacer
import com.embryo.commons.home.SampleScaffold

@Composable
fun ParticleAnimationScreen(
    onBackClick: OnClick,
) {
    SampleScaffold(
        title = "Particle Animation",
        onBackClick = onBackClick,
    ) {
        val automatic = remember { mutableStateOf(true) }
        val transition = rememberInfiniteTransition("particle_animation")
        val manualProgress = remember { mutableFloatStateOf(0f) }

        HeightSpacer(16.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Automatic"
            )
            Switch(
                checked = automatic.value,
                onCheckedChange = { automatic.value = it }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            if (automatic.value) {
                val automaticProgress = transition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 5000),
                    ),
                    label = "particle_animation_progress"
                )
                Explosion(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    progress = automaticProgress.value,
                )
            } else {
                Explosion(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    progress = manualProgress.floatValue,
                )
            }
        }
        Slider(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            value = manualProgress.floatValue,
            onValueChange = { manualProgress.floatValue = it },
            enabled = automatic.value.not()
        )
    }
}

@Preview(name = "ParticleAnimationScreen")
@Composable
private fun PreviewParticleAnimationScreen() {
    ParticleAnimationScreen(
        onBackClick = {}
    )
}