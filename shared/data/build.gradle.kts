import org.gradle.declarative.dsl.schema.FqName.Empty.packageName
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android {
        namespace = "com.karigojobs.share.data"
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


    iosArm64()
    iosSimulatorArm64()


    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.shared.model)
                implementation(projects.shared.domain)
                implementation(projects.shared.database)
                implementation(projects.shared.datastore)
                implementation(libs.sqldelight.coroutines)
                api(libs.ktor.client.core)
                api(libs.ktor.client.content.negotiation)
                api(libs.ktor.serialization.kotlinx.json)
                implementation(libs.kotlinx.datetime)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.ktor.client.android)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
    }

}
