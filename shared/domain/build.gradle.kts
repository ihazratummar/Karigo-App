plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.androidLint)
}

kotlin {
    android {
        namespace = "com.karigojobs.share.domain"
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
                api(projects.shared.model)
                api(projects.shared.datastore)
                api(projects.shared.database)

                implementation(libs.kotlin.stdlib)
                // Add KMP dependencies here
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