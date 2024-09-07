pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

        maven { setUrl("https://jitpack.io") }
        jcenter()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()


        maven { setUrl("https://jitpack.io") }
        jcenter()

    }
}

rootProject.name = "New App Spin"
include(":app")
