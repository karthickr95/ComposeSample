package com.embryo.coroutines_sample

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.embryo.commons.home.HomeScreen
import com.embryo.coroutines_sample.screens.FlowTransformSample


@Suppress("ConstPropertyName")
private object CoroutinesSampleRoutes {

    const val Home = "home"
    const val FlowTransform = "flow_transform"

    val allRoutes: Array<String> = arrayOf(
        FlowTransform,
    )

}

fun NavGraphBuilder.coroutinesSampleNavigationGraph(
    navController: NavHostController,
    route: String,
) {
    navigation(
        route = route,
        startDestination = CoroutinesSampleRoutes.Home,
    ) {
        composable(CoroutinesSampleRoutes.Home) {
            HomeScreen(
                routes = CoroutinesSampleRoutes.allRoutes,
                title = "Coroutines Samples",
                onBackClick = navController::popBackStack,
                onNavClick = { route -> navController.navigate(route) }
            )
        }
        composable(CoroutinesSampleRoutes.FlowTransform) {
            FlowTransformSample(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}