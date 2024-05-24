plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "com.example.logapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.logapp"
        minSdk = 24
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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx.v170)
    implementation(libs.androidx.appcompat.v140)
    implementation(libs.material.v150)
    implementation(libs.androidx.constraintlayout.v213)
    implementation(libs.androidx.lifecycle.livedata.ktx.v240)
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v240)
    implementation(libs.androidx.navigation.fragment.ktx.v240)
    implementation(libs.androidx.navigation.ui.ktx.v240)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.jetbrains.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v113)
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
