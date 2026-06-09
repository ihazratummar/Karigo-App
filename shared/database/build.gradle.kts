import org.gradle.declarative.dsl.schema.FqName.Empty.packageName
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.sql.delight)
}

kotlin {
    android {
        namespace = "com.karigojobs.share.database"
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
                implementation(projects.shared.model)

                implementation(libs.kotlin.stdlib)
                implementation(libs.sqldelight.coroutines)
                implementation(libs.sqldelight.common)
                implementation(libs.kotlinx.datetime)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.sqldelight.android.driver)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.sqldelight.native.driver)
            }
        }
    }

}

sqldelight {
    databases {
        create("KarigojobsDatabase") {
            packageName = "com.karigojobs.shared.database"
            srcDirs("src/commonMain/sqldelight")
        }
        linkSqlite.set(true)
    }
}
