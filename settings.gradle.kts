pluginManagement {
    includeBuild("build-logic")

    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "PrivateCard"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")
include(":core:common")
include(":core:async")
include(":core:model")
include(":core:domain")
include(":core:data")
include(":core:datastore")
include(":core:datastore-proto")
include(":core:database")
include(":core:designsystem")
include(":core:network:network")
include(":core:network:demo")
include(":core:network:ktor")
include(":core:ui")
include(":device:model")
include(":device:provider:avd")
include(":device:provider:apollo")
include(":device:provider:nexgo")
include(":device:provider:pax")
include(":device:sdk:apollo")
include(":device:sdk:nexgo")
include(":device:sdk:pax")
include(":feature:feature1")
 
