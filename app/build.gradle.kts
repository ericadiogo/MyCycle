plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.ericadiogo.mycycle"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ericadiogo.mycycle"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.core)
    implementation(libs.appcompat.v131)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.support.annotations)
    implementation(libs.media3.common)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.work.runtime)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.material.calendarview)
    implementation(libs.google.firebase.analytics)
    implementation(libs.okhttp)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.v112)
    androidTestImplementation(libs.espresso.core.v330)
    implementation(libs.gson)
    implementation(libs.firebase.ui.database)
    implementation(libs.appcompat.v161)
    implementation(libs.material.v190)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database.v2030)
    implementation(libs.recyclerview)
    implementation(libs.work.runtime.ktx)
}