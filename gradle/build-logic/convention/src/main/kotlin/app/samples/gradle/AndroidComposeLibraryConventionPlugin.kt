package app.samples.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidComposeLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            configureAndroid(
                compose = true
            )
        }
    }
}