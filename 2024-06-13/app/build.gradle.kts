

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.venturesupport"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.venturesupport"
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("com.naver.maps:map-sdk:3.14.0")
    implementation("com.google.android.gms:play-services-location:18.0.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("androidx.tracing:tracing-perfetto-handshake:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation(libs.androidx.cardview)
    implementation(libs.volley)
    implementation(libs.oauth)
    // MPAndroidChart
    implementation(libs.mpandroidchart)
    implementation("androidx.room:room-runtime-android:2.7.0-alpha02")

    implementation("com.android.billingclient:billing-ktx:5.0.0")
    implementation("com.google.android.gms:play-services-wallet:19.3.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation(libs.billing)
    runtimeOnly ("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
    implementation("kr.iamport:iamport-android:0.9.5")
    implementation (libs.iamport.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}