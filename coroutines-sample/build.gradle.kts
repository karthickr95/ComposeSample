plugins {
    id("app.samples.android.library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.compose.compiler)
}

android {
    namespace = "com.embryo.coroutines_sample"
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(project(":commons"))

    implementation(libs.core)

    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.foundation.layout)
    implementation(libs.animation)
    implementation(libs.material3)
    implementation(libs.ui.tooling.preview)

    implementation(libs.navigation)
    implementation(libs.lifecycle.compose)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}