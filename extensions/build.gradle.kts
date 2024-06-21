@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("app.samples.android.library")
    alias(libs.plugins.kotlin.compose.compiler)
}

android {
    namespace = "com.embryo.extensions"
}

dependencies {

    implementation(libs.core.ktx)
    implementation(platform(libs.compose.bom))
    implementation(libs.foundation.layout)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}