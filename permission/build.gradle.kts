plugins {
    id("app.samples.android.library")
}

android {
    namespace = "com.embryo.permission"
    lint {
        warning += "AutoboxingStateCreation"
    }
}

dependencies {

    implementation(project(":android-extensions"))

    implementation(libs.core)
    implementation(libs.activity)
}