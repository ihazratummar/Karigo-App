plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    android {
        namespace = "com.karigojobs.share.resources"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = 26

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }


    iosArm64()
    iosSimulatorArm64()


    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                api(compose.runtime)
                api(compose.components.resources)
            }
        }
    }

}

compose.resources {
    publicResClass = true
}

tasks.matching { it.name.startsWith("copyAndroid") && it.name.endsWith("ComposeResourcesToAndroidAssets") }.configureEach {
    enabled = false
}