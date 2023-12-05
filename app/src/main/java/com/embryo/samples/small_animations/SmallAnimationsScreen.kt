package com.embryo.samples.small_animations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.embryo.commons.OnClick
import com.embryo.commons.home.SampleScaffold

@Composable
fun SmallAnimationsScreen(
    onBackClick: OnClick,
) {
    SampleScaffold(
        title = "Small Animations",
        onBackClick = onBackClick,
        verticalArrangement = Arrangement.Top,
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize(),
            columns = GridCells.Fixed(2)
        ) {
            item {

            }
        }
    }
}

@Preview(name = "SmallAnimationScreen")
@Composable
private fun PreviewSmallAnimationsScreen() {
    SmallAnimationsScreen(
        onBackClick = {}
    )
}