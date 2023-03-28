plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_15
        targetCompatibility = JavaVersion.VERSION_15
    }
    kotlinOptions {
        jvmTarget = "15"
    }
    composeOptions {
        kotlinCompilerVersion = "1.8.0"
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(project(":core"))
    implementation(project(":domain"))

    // General
    api(Dependencies.Core)
    implementation(Dependencies.Lifecycle)

    // Compose
    api(Dependencies.ComposeUi)
    api(Dependencies.UiTooling)
    api(Dependencies.Material)
    implementation(Dependencies.IconsCore)
    implementation(Dependencies.IconsExt)
    api(Dependencies.ActivityCompose)
    api(Dependencies.Navigation)
    implementation(Dependencies.HiltCompose)

    // DI
    implementation(Dependencies.Hilt)
    kapt(Dependencies.HiltCompiler)

    // ThirdParty
    implementation(Dependencies.Coil)

    // Testing
    testImplementation(Dependencies.JUnit)
    androidTestImplementation(Dependencies.JUnitExt)
    androidTestImplementation(Dependencies.JUnit4)
}