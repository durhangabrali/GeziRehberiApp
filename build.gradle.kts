import com.android.build.gradle.internal.dsl.decorator.SupportedPropertyType.Collection.List.type
import com.android.build.gradle.internal.errors.DeprecationReporterImpl.Companion.clean

buildscript {
    repositories {
        google() // Google's Maven repository
        mavenCentral() // Maven Central repository
    }
    dependencies{
        classpath ("com.google.gms:google-services:4.0.2")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.android.application") version "8.1.3" apply false
    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("com.android.library") version "7.2.2" apply false
}








