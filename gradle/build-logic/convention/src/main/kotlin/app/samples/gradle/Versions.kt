package app.samples.gradle

import org.gradle.api.JavaVersion

object Versions {
    const val COMPILE_SDK = 34
    const val MIN_SDK = 29
    const val TARGET_SDK = 34
    val Java: JavaVersion = JavaVersion.VERSION_17
}