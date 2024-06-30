plugins {
    id("app.samples.android.library")
}

android {
    namespace = "com.embryo.views"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.core)
    implementation(libs.recyclerview)
    implementation(libs.viewpager2)
    implementation(libs.material)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

}