rootProject.name = "Karigo"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":androidApp")
include(":android:ui")

include(":shared:di")
include(":shared:data")
include(":shared:model")
include(":shared:domain")
include(":shared:device")
include(":shared:database")
include(":shared:datastore")
include(":shared:presentation")

include(":feature:homeScreen")
include(":feature:onboarding")
