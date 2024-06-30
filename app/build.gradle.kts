@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("app.samples.android.application")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.compose.compiler)
}

android {
    namespace = "com.embryo.samples"

    defaultConfig {
        applicationId = "com.embryo.samples"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlinOptions {
        freeCompilerArgs = listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-Xcontext-receivers",
        )
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":commons"))
    implementation(project(":navigation"))
    implementation(project(":extensions"))
    implementation(project(":coroutines-sample"))
    implementation(project(":calendar-utils"))
    implementation(project(":permission"))
    implementation(project(":views"))

    implementation(libs.core)
    implementation(libs.lifecycle.runtime)
    implementation(libs.activity.compose)

    implementation(platform(libs.compose.bom))
    implementation(libs.foundation)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.animation)
    implementation(libs.material3)

    implementation(libs.navigation)
    implementation(libs.kotlinx.collection)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    implementation(libs.arrow.core)
    implementation(libs.arrow.fx.coroutines)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}