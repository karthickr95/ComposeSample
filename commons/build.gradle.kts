plugins {
    id("app.samples.android.library")
    alias(libs.plugins.kotlin.compose.compiler)
}

android {
    namespace = "com.embryo.commons"
    buildFeatures {
        compose = true
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

