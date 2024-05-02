package com.embryo.samples.sharedtransition

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.embryo.commons.home.SampleScaffold
import com.embryo.samples.LoremIpsum1
import com.embryo.samples.R

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedElementTransition(
    onBackClick: () -> Unit,
) {
    SampleScaffold(
        title = "Shared Element Transition",
        onBackClick = onBackClick
    ) {
        SharedTransitionLayout {
            var isExpanded by remember { mutableStateOf(false) }
            val boundsTransform = { _: Rect, _: Rect -> tween<Rect>(550) }

            AnimatedContent(
                targetState = isExpanded,
                label = ""
            ) { target ->
                if (!target) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp)
                            .clickable {
                                isExpanded = !isExpanded
                            }
                    ) {
                        Image(
                            modifier = Modifier
                                .sharedElement(
                                    state = rememberSharedContentState(key = "image"),
                                    animatedVisibilityScope = this@AnimatedContent,
                                    boundsTransform = boundsTransform,
                                )
                                .size(130.dp),
                            painter = painterResource(id = R.drawable.iron_man),
                            contentDescription = null
                        )

                        Text(
                            modifier = Modifier
                                .sharedElement(
                                    state = rememberSharedContentState(key = "name"),
                                    animatedVisibilityScope = this@AnimatedContent,
                                    boundsTransform = boundsTransform,
                                )
                                .fillMaxWidth()
                                .padding(12.dp),
                            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ",
                            fontSize = 12.sp,
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                isExpanded = !isExpanded
                            }
                    ) {
                        Image(
                            modifier = Modifier
                                .sharedElement(
                                    state = rememberSharedContentState(key = "image"),
                                    animatedVisibilityScope = this@AnimatedContent,
                                    boundsTransform = boundsTransform,
                                )
                                .fillMaxWidth()
                                .height(320.dp),
                            painter = painterResource(id = R.drawable.iron_man),
                            contentDescription = null
                        )

                        Text(
                            modifier = Modifier
                                .sharedElement(
                                    state = rememberSharedContentState(key = "name"),
                                    animatedVisibilityScope = this@AnimatedContent,
                                    boundsTransform = boundsTransform,
                                )
                                .fillMaxWidth()
                                .padding(21.dp),
                            text = LoremIpsum1,
                            fontSize = 12.sp,
                        )
                    }
                }
            }
        }

    }
}

context(AnimatedVisibilityScope, SharedTransitionScope)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedBoundsList(
    ironManDrawables: IntArray,
    onBackClick: () -> Unit,
    onNavigate: (Int) -> Unit,
) {
    SampleScaffold(
        title = "Shared Bounds List",
        onBackClick = onBackClick
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(ironManDrawables.toList()) { index, drawableId ->
                Row(
                    modifier = Modifier
                        .clickable { onNavigate(index) }
                        .sharedBounds(
                            rememberSharedContentState(key = "iron-man-$drawableId"),
                            animatedVisibilityScope = this@AnimatedVisibilityScope,
                        )
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = drawableId),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .size(100.dp)
                    )

                    Text(
                        text = LoremIpsum1,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}

context(AnimatedVisibilityScope, SharedTransitionScope)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedBoundsDetail(
    drawableId: Int,
    onBackClick: () -> Unit,
) {
    SampleScaffold(
        title = "Shared Bounds Detail",
        onBackClick = onBackClick
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onBackClick)
                .sharedBounds(
                    rememberSharedContentState(key = "iron-man-$drawableId"),
                    animatedVisibilityScope = this@AnimatedVisibilityScope,
                )
        ) {
            Image(
                painterResource(id = drawableId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = LoremIpsum1,
                fontSize = 18.sp,
            )
        }
    }
}