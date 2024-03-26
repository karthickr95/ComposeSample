package com.embryo.samples.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.unit.dp
import com.embryo.commons.home.SampleScaffold
import com.embryo.commons.turquoiseColors
import kotlinx.coroutines.delay

@Composable
fun LookaheadWithAnimateItem(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val visible by produceState(true) {
        while (true) {
            delay(2000)
            value = !value
        }
    }

    SampleScaffold(
        title = "Lazy Layout animate item",
        onBackClick = onBackClick,
        modifier = modifier,
    ) {

        LookaheadScope {
            LazyColumn(Modifier.padding(20.dp)) {
                items(3, key = { it }) {
                    Column(
                        Modifier
                            .animateItem()
                            .clip(RoundedCornerShape(15.dp))
                            .background(turquoiseColors[it])

                    ) {
                        Box(
                            Modifier
                                .requiredHeight(ItemSize.dp)
                                .fillMaxWidth()
                        )
                        AnimatedVisibility(visible = visible) {
                            Box(
                                Modifier
                                    .requiredHeight(ItemSize.dp)
                                    .fillMaxWidth()
                                    .background(Color.White)
                            )
                        }
                    }
                }
            }
        }
    }
}

private const val ItemSize = 100