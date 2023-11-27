package com.embryo.samples.compose_1_6_try

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.embryo.samples.R
import com.embryo.samples.home.HomeScreen

@Suppress("ConstPropertyName")
private object Compose16Routes {

    const val Home = "home"
    const val SeekableAnimation = "seekable_animation"
    const val SeekableAnimation2 = "seekable_animation_2"
    const val TextField = "text_field"
    const val IntermediateLayoutSample = "intermediate_layout_sample"
    const val LookaheadSample = "lookahead_sample"
    const val LookaheadContentSizeAnimation = "lookahead_content_size_animation"
    const val AnchorDraggable = "anchor_draggable"

    val allRoutes: Array<String> = arrayOf(
        SeekableAnimation,
        SeekableAnimation2,
        TextField,
        IntermediateLayoutSample,
        LookaheadSample,
        LookaheadContentSizeAnimation,
        AnchorDraggable,
    )
}

fun NavGraphBuilder.compose16NavigationGraph(
    navController: NavHostController,
    route: String,
) {
    navigation(
        route = route,
        startDestination = Compose16Routes.Home,
    ) {
        composable(Compose16Routes.Home) {
            HomeScreen(
                routes = Compose16Routes.allRoutes,
                titleRes = R.string.compose_1_6_title,
                onBackClick = navController::popBackStack,
                onNavClick = { route -> navController.navigate(route) }
            )
        }
        composable(Compose16Routes.SeekableAnimation) {
            SeekableAnimation(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Compose16Routes.SeekableAnimation2) {
            SeekableAnimation2(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Compose16Routes.TextField) {
            TextFieldSample(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Compose16Routes.IntermediateLayoutSample) {
            IntermediateLayoutSample(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Compose16Routes.LookaheadSample) {
            LookaheadLayoutCoordinatesSample(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Compose16Routes.LookaheadContentSizeAnimation) {
            AnimateContentSizeAfterLookaheadPass(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Compose16Routes.AnchorDraggable) {
            AnchoredDraggableSample(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}