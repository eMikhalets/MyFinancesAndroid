plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = Configuration.namespace
    compileSdk = 33

    defaultConfig {
        applicationId = Configuration.applicationId
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        versionCode = Configuration.versionCode
        versionName = Configuration.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
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
        compose = true
    }
    composeOptions {
        kotlinCompilerVersion = "1.8.0"
        kotlinCompilerExtensionVersion = "1.4.0"
    }
}

dependencies {

    // General
    implementation(Dependencies.Core)
    implementation(Dependencies.Lifecycle)
    implementation(Dependencies.Coroutines)

    // Compose
    implementation(Dependencies.ComposeUi)
    implementation(Dependencies.UiTooling)
    implementation(Dependencies.Material)
    implementation(Dependencies.IconsCore)
    implementation(Dependencies.IconsExt)
    implementation(Dependencies.ActivityCompose)
    implementation(Dependencies.Navigation)
    implementation(Dependencies.HiltCompose)

    // Database
    implementation(Dependencies.Room)
    implementation(Dependencies.RoomKtx)
    kapt(Dependencies.RoomCompiler)

    // DI
    implementation(Dependencies.Hilt)
    kapt(Dependencies.HiltCompiler)

    // ThirdParty
    implementation(Dependencies.Coil)

    // Testing
    implementation(Dependencies.JUnit)
    implementation(Dependencies.JUnitExt)
    implementation(Dependencies.JUnit4)
    implementation(Dependencies.UiToolingTest)
    implementation(Dependencies.TestManifest)
}