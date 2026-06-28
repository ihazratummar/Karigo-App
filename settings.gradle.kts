rootProject.name = "Karigojobs"
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
include(":android:services")

include(":shared:di")
include(":shared:data")
include(":shared:model")
include(":shared:utils")
include(":shared:domain")
include(":shared:device")
include(":shared:resources")
include(":shared:database")
include(":shared:datastore")
include(":shared:presentation")

include(":feature:homeScreen")
include(":feature:onboarding")
include(":feature:materials")
include(":feature:job")
include(":feature:client")
include(":feature:siteEstimate")
include(":feature:settings")
