plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlin.multiplatform)
    `nsexception-kt-publish`
}

kotlin {
    explicitApi()

    macosX64()
    macosArm64()
    iosArm64()
    iosX64()
    iosSimulatorArm64()
    watchosArm32()
    watchosArm64()
    watchosX64()
    watchosSimulatorArm64()
    tvosArm64()
    tvosX64()
    tvosSimulatorArm64()

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
