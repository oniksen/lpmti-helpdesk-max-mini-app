plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    js {
        browser()
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.navigation.api)
        }
    }
}