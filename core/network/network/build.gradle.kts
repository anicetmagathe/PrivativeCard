plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "core.network"
}

dependencies {
    implementation(projects.core.common)
    api(projects.core.model)
    implementation(libs.kotlinx.serialization.json)
}