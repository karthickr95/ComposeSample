pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://androidx.dev/snapshots/builds/11618358/artifacts/repository")
    }
}

rootProject.name = "Samples"
include(":app")
include(":commons")
include(":navigation")
include(":extensions")
include(":coroutines-sample")
include(":benchmark")
