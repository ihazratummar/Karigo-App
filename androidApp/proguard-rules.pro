# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/hazratummar/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.

# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep rules here:

# Kotlin Serialization
-keepattributes *Annotation*, EnclosingMethod, Signature
-keepclassmembernames class * {
    @kotlinx.serialization.SerialName <fields>;
}

# Koin
-keep class io.insertkoin.** { *; }

# SQLDelight
-keep class com.squareup.sqldelight.** { *; }

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepnames class kotlinx.coroutines.android.AndroidExceptionPreHandler {}
-keepnames class kotlinx.coroutines.android.AndroidDispatcherFactory {}
-keep class kotlinx.coroutines.android.** { *; }

# Compose
-keep class androidx.compose.ui.platform.** { *; }
-keep class androidx.compose.runtime.** { *; }

# Navigation
-keep class androidx.navigation.** { *; }

# Karigo specific rules (keep model classes if needed for serialization/reflection)
-keep class com.karigojobs.share.model.** { *; }
-keep class com.karigojobs.data.dto.** { *; }

# WorkManager & Room
-keep class androidx.work.impl.** { *; }
-keep class androidx.room.** { *; }

# Firebase Analytics & Crashlytics
-keep class com.google.firebase.analytics.** { *; }
-keep class com.google.android.gms.measurement.** { *; }
-keep class com.google.firebase.crashlytics.** { *; }

# Reusable AOSP printing bypass bridge
-keep class android.print.PdfPrint {
    *;
}

