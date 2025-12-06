plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.hilt)
}

android {
    namespace = "mg.anet.dll.device.model"
}

dependencies {
    api(libs.draw.receipt)
}