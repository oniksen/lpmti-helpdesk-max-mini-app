plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    js {
        browser()
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.parking.api)
            implementation(projects.maxminiappapi.api)
            implementation(projects.core.navigation.api)
            implementation(projects.core.uiadaptive)

            implementation(libs.bundles.compose)
            implementation(libs.bundles.koin)
        }
        jvmMain.dependencies {
            implementation(libs.compose.ui)
        }
    }
}