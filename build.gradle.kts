plugins {
    alias(libs.plugins.kotlinMultiplatform).apply(false)
}

allprojects {
    val kvalidVersion: String by project

    group = "io.github.mrkekovich"
    version = kvalidVersion
}