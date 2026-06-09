plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.androidLint)
}

kotlin {
    android {
        namespace = "com.karigojobs.share.presentation"
        compileSdk {
            version = release(36) {
                minorApiLevel = 1
            }
        }
        minSdk = 26

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }


    iosX64()
    iosArm64()
    iosSimulatorArm64()


    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.shared.domain)
                implementation(projects.shared.model)
                implementation(libs.kotlin.stdlib)
                // Add KMP dependencies here

                implementation(libs.lifecycle.viewmodel)
            }
        }


        androidMain {
            dependencies {

            }
        }


        iosMain {
            dependencies {

            }
        }
    }

}