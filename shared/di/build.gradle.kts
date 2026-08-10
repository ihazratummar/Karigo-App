import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.androidLint)
}

kotlin {
    android {
        namespace = "com.karigojobs.share.di"
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


    val xcfName = "sharedNewKit"

    iosArm64 {
        binaries.framework {
            baseName = xcfName
            isStatic = true
            export(projects.shared.domain)
            export(projects.shared.presentation)
            export(projects.shared.data)
            export(projects.shared.model)
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
            isStatic = true
            export(projects.shared.domain)
            export(projects.shared.presentation)
            export(projects.shared.data)
            export(projects.shared.model)
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
                api(projects.shared.datastore)
                api(projects.shared.database)
                api(projects.shared.device)
                api(projects.shared.domain)
                api(projects.shared.data)
                api(projects.shared.presentation)
                api(projects.shared.model)
                
                implementation(libs.kotlin.stdlib)

                api(libs.koin.core)
                api(libs.koin.compose.viewmodel)

                api(libs.ktor.client.core)
                api(libs.ktor.client.content.negotiation)
                api(libs.ktor.serialization.kotlinx.json)

            }
        }

        androidMain {
            dependencies {
                implementation(libs.koin.android)
                implementation(libs.ktor.client.android)
                implementation(libs.play.billing.ktx)
            }
        }


        iosMain {
            dependencies {
                implementation(libs.koin.core)
                implementation(libs.ktor.client.darwin)
            }
        }
    }

}