plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.logapp2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.logapp2"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.play.services.analytics.impl)
    implementation(libs.androidx.recyclerview)
    //noinspection UseTomlInstead
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    //noinspection UseTomlInstead
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android")
    implementation(libs.legacy.support.core.utils)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.constraintlayout.v113)
    implementation(libs.androidx.security.crypto)
    //noinspection UseTomlInstead
    implementation("androidx.fragment:fragment-ktx")
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v240)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.moshi.kotlin)
    implementation(libs.logging.interceptor)
    implementation(libs.lottie)
    //mplementation (com.squareup.retrofit2:retrofit:2.9.0)
    //implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.constraintlayout.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}