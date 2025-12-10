plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.library.compose)
    alias(libs.plugins.convention.android.hilt)
}

android {
    namespace = "core.designsystem"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.androidx.compose.material3)
    api(libs.coil.kt.compose)
    api(libs.coil.network.okhttp)
    implementation(libs.androidx.material.icons.extended.android)
    api(libs.lottie)
}