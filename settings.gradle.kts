rootProject.name = "max-helpdesk"

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":shared")
include(":webApp")
include(":core")
include(":core:di")
include(":maxminiappapi")
include(":maxminiappapi:api")
include(":maxminiappapi:impl")
include(":features")
include(":features:qrscan")
include(":features:qrscan:api")
include(":features:qrscan:impl")
include(":core:navigation")
include(":core:navigation:api")
include(":core:navigation:impl")