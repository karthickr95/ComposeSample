plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose.compiler)
}

android {
    namespace = "com.embryo.commons"
    compileSdk = 33

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies {

    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.foundation.layout)
    implementation(libs.animation)
    implementation(libs.material3)
    implementation(libs.ui.tooling.preview)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

}

