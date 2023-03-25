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
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
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

    implementation(project(":core"))
    implementation(project(":domain"))

    // Database
    api(Dependencies.Room)
    implementation(Dependencies.RoomKtx)
    kapt(Dependencies.RoomCompiler)

    // DI
    implementation(Dependencies.Hilt)
    kapt(Dependencies.HiltCompiler)

    // Testing
    testImplementation(Dependencies.JUnit)
}