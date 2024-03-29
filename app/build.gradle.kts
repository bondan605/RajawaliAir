plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")

    //firebase
    id("com.google.firebase.crashlytics")

    //navigation
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.rajawali.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.rajawali.app"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
//        jvmTarget = "1.8"
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":common_resources"))

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.annotation:annotation:1.7.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")

    //navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")

    //constraintLayout
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //endless scroll of recyclerview
    implementation ("com.github.beksomega:loopinglayout:0.5.0")

    //page indicator for recyclerview
    implementation("com.tbuonomo:dotsindicator:5.0")

    //coroutine
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    //koin
    implementation ("io.insert-koin:koin-core:3.2.2")
    implementation ("io.insert-koin:koin-android:3.3.0")

    //livedata
    api ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    //timber
    implementation ("com.jakewharton.timber:timber:4.7.1")

}