package com.embryo.samples.sharedtransition

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
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
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = drawableId),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .size(100.dp)
                            .sharedBounds(
                                rememberSharedContentState(key = "iron-man-$drawableId"),
                                animatedVisibilityScope = this@AnimatedVisibilityScope,
                            )
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
        ) {
            Image(
                modifier = Modifier
                    .sharedBounds(
                        rememberSharedContentState(key = "iron-man-$drawableId"),
                        animatedVisibilityScope = this@AnimatedVisibilityScope,
                    )
                    .aspectRatio(1f)
                    .fillMaxWidth()
                    .clip(CircleShape),
                painter = painterResource(id = drawableId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = LoremIpsum1,
                fontSize = 18.sp,
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedElementWithCallerManagedVisibility(
    onBackClick: () -> Unit,
) {
    SampleScaffold(
        title = "Shared Element With Caller Managed Visibility",
        onBackClick = onBackClick
    ) {
        var selectFirst by remember { mutableStateOf(true) }
        val key = remember { Any() }
        SharedTransitionLayout(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
                .clickable {
                    selectFirst = !selectFirst
                }
        ) {
            Box(
                Modifier
                    .sharedElementWithCallerManagedVisibility(
                        sharedContentState = rememberSharedContentState(key = key),
                        !selectFirst,
                        boundsTransform = { _, _ ->
                            tween(2000)
                        }
                    )
                    .background(Color.Red)
                    .size(100.dp)
            ) {
                Text(if (!selectFirst) "false" else "true", color = Color.White)
            }
            Box(
                Modifier
                    .offset(180.dp, 180.dp)
                    .sharedElementWithCallerManagedVisibility(
                        rememberSharedContentState(key = key),
                        selectFirst,
                        boundsTransform = { _, _ ->
                            tween(2000)
                        }
                    )
                    .alpha(0.5f)
                    .background(Color.Blue)
                    .size(180.dp)
            ) {
                Text(if (selectFirst) "false" else "true", color = Color.White)
            }
        }
    }
}