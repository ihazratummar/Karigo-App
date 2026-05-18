import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}
dependencies {

    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material3)

    implementation(libs.compose.uiToolingPreview)
    implementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.compose.uiTooling)


    implementation(libs.androidx.compose.adaptive)

}

android {
    namespace = "com.karigo.app.android.ui"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }


    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}