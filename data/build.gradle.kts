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
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_15
        targetCompatibility = JavaVersion.VERSION_15
    }
    kotlinOptions {
        jvmTarget = "15"
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