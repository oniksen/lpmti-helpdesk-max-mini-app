plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
}

kotlin {
    js {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.navigation.api)
            implementation(projects.features.parking.api)
            implementation(projects.features.home.api)

            implementation(libs.jetbrains.navigation3.ui)
            implementation(libs.bundles.koin)
            implementation(libs.bundles.compose)
        }
    }
}