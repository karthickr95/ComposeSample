package com.embryo.commons.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.ExperimentalTransitionApi
import androidx.compose.animation.core.SeekableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier


@Composable
fun <S> rememberSeekableTransitionState(initialValue: S): SeekableTransitionState<S> {
    return remember {
        SeekableTransitionState(initialValue)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <S> SeekableTransitionState<S>.AnimatedVisibility(
    visible: (S) -> Boolean,
    modifier: Modifier = Modifier,
    enter: EnterTransition = fadeIn() + expandIn(),
    exit: ExitTransition = shrinkOut() + fadeOut(),
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    val transition = rememberTransition(transitionState = this)
    transition.AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = enter,
        exit = exit,
        content = content,
    )
}