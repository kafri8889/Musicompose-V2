pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(uri("https://plugins.gradle.org/m2/"))
        maven(uri("https://jitpack.io/"))
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(uri("https://plugins.gradle.org/m2/"))
        maven(uri("https://jitpack.io/"))
    }
}

rootProject.name = "Musicompose"
include(":app")
