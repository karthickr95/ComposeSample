package com.embryo.samples.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.embryo.commons.Callback
import com.embryo.navigation.Routes
import com.embryo.samples.compose_1_6_try.*
import com.embryo.commons.home.HomeScreen
import com.embryo.coroutines_sample.coroutinesSampleNavigationGraph
import com.embryo.samples.animations.*
import com.embryo.samples.particle.ParticleAnimationScreen
import com.embryo.samples.small_animations.SmallAnimationsScreen

@Composable
fun SamplesNavHost(
    onCloseAppAction: Callback,
    modifier: Modifier = Modifier,
    startDestination: String = Routes.Home,
) {

    val navController: NavHostController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Routes.Home) {
            HomeScreen(
                title = "Home",
                routes = Routes.allRoutes,
                onBackClick = onCloseAppAction,
                onNavClick = { route ->
                    navController.navigate(route)
                }
            )
        }
        compose16NavigationGraph(navController, Routes.Compose16Try)
        coroutinesSampleNavigationGraph(navController, Routes.CoroutinesSamples)
        composable(Routes.ParticleAnimation) {
            ParticleAnimationScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Routes.SmallAnimations) {
            SmallAnimationsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Routes.GestureDrawer) {
            GestureDrawer(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Routes.SmoothLineGraph) {
            SmoothLineGraph(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Routes.GradientAlongPathAnimation) {
            GradientAlongPathAnimation(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Routes.BouncyRopes) {
            BouncyRopes(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Routes.AllTrackers) {
            AllTrackersCard(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Routes.LazyColumnAnimateItem) {
            LookaheadWithAnimateItem(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Routes.LookaheadWithDisappearingMovableContentDemo) {
            LookaheadWithDisappearingMovableContentDemo(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Routes.ContainerTransform) {
            ContainerTransform(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Routes.DemoScreen) {
            DemoScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}