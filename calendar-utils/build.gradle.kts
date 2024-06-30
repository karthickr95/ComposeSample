plugins {
    id("app.samples.android.library")
}

android {
    namespace = "app.embryo.calendar.utils"
}

dependencies {

    implementation(libs.core)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}