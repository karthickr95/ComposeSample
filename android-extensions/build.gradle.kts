plugins {
    id("app.samples.android.library")
}

android {
    namespace = "com.embryo.android.extensions"
    //buildToolsVersion = libs.versions.buildtools.get()
}

dependencies {

    implementation(libs.core)
    //implementation(libs.material)

    //testImplementation(project(":testLib"))

    testImplementation(libs.junit)
    //testImplementation(libs.roboelectric)
    /*testImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.core.testing)*/
}