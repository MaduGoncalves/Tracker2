plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.tracker"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.tracker"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}


dependencies {
    val room_version = "2.6.1";
    implementation(
        "androidx.room:room-runtime:$room_version"
    );
    annotationProcessor(
        "androidx.room:room-compiler:$room_version"
    );
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
    implementation("androidx.cardview:cardview:1.0.0")
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
}