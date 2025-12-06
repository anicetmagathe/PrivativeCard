plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.hilt)
}

android {
    namespace = "core.domain"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.common)
    implementation(projects.core.async)
    implementation(projects.device.model)
}