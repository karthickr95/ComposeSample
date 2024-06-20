package com.embryo.samples.navigation

import android.content.Intent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.embryo.commons.Callback
import com.embryo.commons.home.HomeScreen
import com.embryo.coroutines_sample.coroutinesSampleNavigationGraph
import com.embryo.navigation.Routes
import com.embryo.samples.R
import com.embryo.samples.animations.AllTrackersCard
import com.embryo.samples.animations.BouncyRopes
import com.embryo.samples.animations.ContainerTransform
import com.embryo.samples.animations.DemoScreen
import com.embryo.samples.animations.GestureDrawer
import com.embryo.samples.animations.GradientAlongPathAnimation
import com.embryo.samples.animations.LookaheadWithAnimateItem
import com.embryo.samples.animations.LookaheadWithDisappearingMovableContentDemo
import com.embryo.samples.animations.SmoothLineGraph
import com.embryo.samples.compose_1_6_try.compose16NavigationGraph
import com.embryo.samples.lifecycle.AActivity
import com.embryo.samples.particle.ParticleAnimationScreen
import com.embryo.samples.sharedtransition.SharedBoundsDetail
import com.embryo.samples.sharedtransition.SharedBoundsList
import com.embryo.samples.sharedtransition.SharedElementTransition
import com.embryo.samples.sharedtransition.SharedElementWithCallerManagedVisibility
import com.embryo.samples.small_animations.SmallAnimationsScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SamplesNavHost(
    onCloseAppAction: Callback,
    modifier: Modifier = Modifier,
    startDestination: String = Routes.Home,
) {

    val context = LocalContext.current


    val ironManDrawables = intArrayOf(
        R.drawable.iron_man,
        R.drawable.iron_man_2,
        R.drawable.iron_man_3,
        R.drawable.iron_man_4,
    )

    val navController: NavHostController = rememberNavController()
    SharedTransitionScope {
        NavHost(
            modifier = it.then(modifier),
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
                    },
                    startLifecycleActivity = {
                        context.startActivity(Intent(context, AActivity::class.java))
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
            composable(Routes.SharedElementTransition) {
                SharedElementTransition(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(Routes.SharedBoundsList) {
                SharedBoundsList(
                    ironManDrawables = ironManDrawables,
                    onBackClick = { navController.popBackStack() },
                    onNavigate = { navController.navigate("${Routes.SharedBoundsDetail}/$it") }
                )
            }
            composable(
                route = Routes.SharedBoundsDetail + "/{index}",
                arguments = listOf(navArgument("index") { type = NavType.IntType })
            ) { backStackEntry ->
                val ironManIndex = backStackEntry.arguments?.getInt("index") ?: 0
                SharedBoundsDetail(
                    drawableId = ironManDrawables[ironManIndex],
                    onBackClick = { navController.popBackStack() },
                )
            }
            composable(Routes.SharedElementWithCallerManagedVisibility) {
                SharedElementWithCallerManagedVisibility(
                    onBackClick = { navController.popBackStack() },
                )
            }
        }
    }
}