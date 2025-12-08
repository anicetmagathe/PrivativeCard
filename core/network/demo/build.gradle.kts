plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "core.network.demo"
}

dependencies {
    api(projects.core.network.network)
    implementation(projects.core.async)

    implementation(libs.kotlinx.serialization.json)
}