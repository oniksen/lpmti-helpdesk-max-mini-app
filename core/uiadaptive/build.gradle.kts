plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    jvm()

    js { browser() }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.compose)
        }
    }
}