import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}
dependencies {
    implementation(projects.android.ui)

    implementation(projects.shared.model)
    api(projects.shared.presentation)

    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material3)

    implementation(libs.compose.uiToolingPreview)
    implementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.compose.uiTooling)



}

android {
    namespace = "com.karigojobs.app.feature.settings"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        consumerProguardFiles("consumer-rules.pro")
    }


    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}