plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = Configuration.namespace
    compileSdk = 33

    defaultConfig {
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(project(":core"))
    implementation(project(":domain"))

    // General
    implementation(Dependencies.Core)
    implementation(Dependencies.Lifecycle)

    // Compose
    implementation(Dependencies.ComposeUi)
    implementation(Dependencies.UiTooling)
    implementation(Dependencies.Material)
    implementation(Dependencies.IconsCore)
    implementation(Dependencies.IconsExt)
    implementation(Dependencies.ActivityCompose)
    implementation(Dependencies.Navigation)
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