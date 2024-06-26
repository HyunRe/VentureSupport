plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.test2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.test2"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // DrawerLayout
    implementation("androidx.drawerlayout:drawerlayout:1.1.1")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.6.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.6.0")
    implementation ("com.google.code.gson:gson:2.8.6")
    implementation ("com.squareup.okhttp3:logging-interceptor:3.11.0")
    implementation("androidx.tracing:tracing-perfetto-handshake:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")

    // Calender
    //implementation("com.prolificinteractive:material-calendarview:1.4.3")

    // MPAndroidChart
    // implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("androidx.room:room-runtime-android:2.7.0-alpha02")

    // ViewModel
    implementation ("androidx.fragment:fragment-ktx:1.3.0")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // Coroutine
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}