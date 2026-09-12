import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}
dependencies {
    api(projects.shared.di)
    implementation(libs.koin.android)

    implementation(projects.android.services)
    api(projects.feature.onboarding)
    implementation(projects.feature.homeScreen)
    implementation(projects.feature.job)
    implementation(projects.feature.client)
    implementation(projects.feature.materials)
    implementation(projects.feature.siteEstimate)
    implementation(projects.feature.settings)

    implementation(projects.shared.presentation)
    implementation(projects.shared.domain)
    implementation(projects.shared.model)
    implementation(projects.shared.device)
    implementation(projects.shared.resources)

    implementation(libs.androidx.compose.adaptive)
    implementation(projects.android.ui)
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material3)
    implementation(libs.compose.uiToolingPreview)
    debugImplementation(libs.compose.uiTooling)

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    debugImplementation(libs.leakcanary)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
}

android {
    namespace = "com.karigojobs.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.karigojobs.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 38
        versionName = "1.3.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    sourceSets {
        getByName("main") {
            assets.srcDirs(
                "${layout.buildDirectory.get().asFile}/generated/customAssets"
            )
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

val copyComposeResourcesForAndroidApp = tasks.register<Copy>("copyComposeResourcesForAndroidApp") {
    val sourceDir = project(":shared:resources").layout.buildDirectory.dir("generated/compose/resourceGenerator/preparedResources/commonMain/composeResources")
    val destDir = layout.buildDirectory.dir("generated/customAssets/composeResources/karigojobs.shared.resources.generated.resources")
    
    from(sourceDir)
    into(destDir)
    
    dependsOn(project(":shared:resources").tasks.matching { it.name.contains("ComposeResources") || it.name.contains("XmlValueResources") })
}

tasks.matching { 
    it.name.contains("lint", ignoreCase = true) || 
    (it.name.startsWith("merge") && it.name.endsWith("Assets")) ||
    (it.name.startsWith("package") && it.name.endsWith("Assets"))
}.configureEach {
    dependsOn(copyComposeResourcesForAndroidApp)
}