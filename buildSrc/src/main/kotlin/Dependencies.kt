object Versions {
    internal const val Core = "1.9.0"
    internal const val Lifecycle = "2.6.1"
    internal const val Coroutines = "1.6.4"

    internal const val Compose = "1.4.0"
    internal const val ActivityCompose = "1.7.0"
    internal const val NavigationCompose = "2.5.3"
    internal const val HiltCompose = "1.0.0"

    internal const val Room = "2.5.0"
    internal const val Hilt = "2.43.2"
    internal const val Coil = "2.2.1"

    internal const val JUnit = "4.13.2"
    internal const val ExtJUnit = "1.1.5"
}

object Dependencies {
    const val Core = "androidx.core:core-ktx:${Versions.Core}"
    const val Lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Lifecycle}"
    const val Coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Coroutines}"

    const val ComposeUi = "androidx.compose.ui:ui:${Versions.Compose}"
    const val UiTooling = "androidx.compose.ui:ui-tooling:${Versions.Compose}"
    const val Material = "androidx.compose.material:material:${Versions.Compose}"
    const val IconsCore = "androidx.compose.material:material-icons-core:${Versions.Compose}"
    const val IconsExt = "androidx.compose.material:material-icons-extended:${Versions.Compose}"

    const val ActivityCompose = "androidx.activity:activity-compose:${Versions.ActivityCompose}"
    const val Navigation = "androidx.navigation:navigation-compose:${Versions.NavigationCompose}"
    const val HiltCompose = "androidx.hilt:hilt-navigation-compose:${Versions.HiltCompose}"

    const val Room = "androidx.room:room-runtime:${Versions.Room}"
    const val RoomKtx = "androidx.room:room-ktx:${Versions.Room}"
    const val RoomCompiler = "androidx.room:room-compiler:${Versions.Room}"

    const val Hilt = "com.google.dagger:hilt-android:${Versions.Hilt}"
    const val HiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Hilt}"

    const val Coil = "io.coil-kt:coil:${Versions.Coil}"

    const val JUnit = "junit:junit:${Versions.JUnit}"
    const val JUnitExt = "androidx.test.ext:junit:${Versions.ExtJUnit}"
    const val JUnit4 = "androidx.compose.ui:ui-test-junit4:${Versions.Compose}"
    const val UiToolingTest = "androidx.compose.ui:ui-tooling:${Versions.Compose}"
    const val TestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.Compose}"
}