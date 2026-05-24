import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.androidLint)
}

kotlin {
    android {
        namespace = "com.karigo.share.di"
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


    val xcfName = "sharedNewKit"

    iosX64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    targets.withType<KotlinNativeTarget> {
        binaries.all {
            linkerOpts("-lsqlite3")
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.shared.datastore)
                implementation(projects.shared.database)
                implementation(projects.shared.device)
                implementation(projects.shared.domain)
                implementation(projects.shared.data)
                implementation(projects.shared.presentation)

                implementation(libs.kotlin.stdlib)

                api(libs.koin.core)
                api(libs.koin.compose.viewmodel)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.koin.android)
            }
        }


        iosMain {
            dependencies {
                implementation(libs.koin.core)
            }
        }
    }

}