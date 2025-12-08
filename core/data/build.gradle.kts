plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.android.hilt)
}

android {
    namespace = "core.data"
}

dependencies {
    implementation(projects.core.model)
    demoImplementation(projects.core.network.demo)
    prodImplementation(projects.core.network.ktor)
}