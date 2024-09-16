plugins {
    // NOTE: do not use plugin com.android.application here
    // because this is a library project
//    alias(libs.plugins.android.application)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "vn.kietnguyendev.translateapp.core.database"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
}

dependencies {
    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.compiler)
    implementation(libs.room.ktx)

    // DI
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}