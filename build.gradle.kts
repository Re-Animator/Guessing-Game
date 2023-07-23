// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
}

buildscript {
    repositories {
        google()
    }

    dependencies {
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}