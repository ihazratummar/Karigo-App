plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.androidLint)
}

kotlin {
    android {
        namespace = "com.karigojobs.share.presentation"
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
                api(projects.shared.domain)
                api(projects.shared.model)
                api(projects.shared.utils)
                api(projects.shared.device)
                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.datetime)
                // Add KMP dependencies here

                implementation(libs.lifecycle.viewmodel)
                implementation(libs.kotlinx.datetime)
            }
        }
    }

}