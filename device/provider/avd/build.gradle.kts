plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.hilt)
}

android {
    namespace = "mg.anet.dll.device"
}

dependencies {
    api(projects.device.model)
}