plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvm()

    js {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.home.api)
            implementation(projects.core.navigation.api)
            implementation(projects.features.qrscan.api)

            implementation(libs.bundles.compose)
            implementation(libs.bundles.koin)
        }
        jvmMain.dependencies {
            implementation(libs.compose.ui)
        }
    }
}