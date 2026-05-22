import org.gradle.declarative.dsl.schema.FqName.Empty.packageName
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.sql.delight)
}

kotlin {
    android {
        namespace = "com.karigo.share.database"
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


    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.shared.model)

                implementation(libs.kotlin.stdlib)
                implementation(libs.sqldelight.coroutines)
                implementation(libs.sqldelight.common)
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
        create("KarigoDatabase") {
            packageName = "com.karigo.shared.database"
            srcDirs("src/commonMain/sqldelight")
        }
        linkSqlite.set(true)
    }
}
