package com.embryo.navigation

@Suppress("ConstPropertyName")
object Routes {

    const val Home = "home"
    const val ParticleAnimation = "particle_animation"
    const val SmallAnimations = "small_animations"
    const val Compose16Try = "compose_1_6_try"
    const val GestureDrawer = "gesture_drawer"
    const val SmoothLineGraph = "smooth_line_graph"

    val allRoutes: Array<String> = arrayOf(
        ParticleAnimation,
        SmallAnimations,
        Compose16Try,
        GestureDrawer,
        SmoothLineGraph,
    )

}