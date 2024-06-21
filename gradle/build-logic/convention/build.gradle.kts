plugins {
    alias(libs.plugins.kotlin.dsl)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(libs.versions.java.get())
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("root") {
            id = "app.samples.root"
            implementationClass = "app.samples.gradle.RootConventionPlugin"
        }
        register("androidApplication") {
            id = "app.samples.android.application"
            implementationClass = "app.samples.gradle.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "app.samples.android.library"
            implementationClass = "app.samples.gradle.AndroidLibraryConventionPlugin"
        }
        register("androidComposeLibrary") {
            id = "app.samples.android.library.compose"
            implementationClass = "app.samples.gradle.AndroidComposeLibraryConventionPlugin"
        }
        register("kotlinAndroid") {
            id = "app.samples.kotlin.android"
            implementationClass = "app.samples.gradle.KotlinAndroidConventionPlugin"
        }
    }
}