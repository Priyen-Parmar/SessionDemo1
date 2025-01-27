plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.session.demo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.session.demo"
        minSdk = 21
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    //implementation("com.intuit.sdp:sdp-android:1.1.1")
    //implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation(libs.glide)
    implementation(libs.sdpandroid)

    implementation(libs.retrofit)
    implementation(libs.retrofitgson)
    implementation(libs.retrofitconvertor)
    implementation(libs.circleimage)

    implementation(libs.fishbun)
    implementation(libs.coil)
    implementation(libs.razorpay)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}