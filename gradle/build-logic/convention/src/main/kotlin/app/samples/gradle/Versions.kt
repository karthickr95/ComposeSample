package app.samples.gradle

import org.gradle.api.JavaVersion

object Versions {
    const val COMPILE_SDK = 35
    const val MIN_SDK = 29
    const val TARGET_SDK = 35
    val Java: JavaVersion = JavaVersion.VERSION_17
}