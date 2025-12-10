import mg.anet.template.VersionInfo

plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.convention.android.application.compose)
    alias(libs.plugins.convention.android.hilt)
    alias(libs.plugins.convention.application.version)
}

android {
    namespace = "mg.moneytech.privatecard"

    defaultConfig {
        applicationId = "mg.moneytech.privatecard"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }

        jniLibs.useLegacyPackaging = true
    }
}

appVersionExtension {
    name = "App"
    version = VersionInfo(0, 0, 1)
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(projects.core.async)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.coil.kt.compose)
    implementation(libs.zoomable)

    paxImplementation(projects.device.provider.pax)
    avdImplementation(projects.device.provider.avd)
    apolloImplementation(projects.device.provider.apollo)
    nexgoImplementation(projects.device.provider.nexgo)
}