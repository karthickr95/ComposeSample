plugins {
    id("app.samples.android.library")
}

android {
    namespace = "com.embryo.navigation"
}

dependencies {

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}