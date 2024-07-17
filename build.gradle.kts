plugins {
    alias(libs.plugins.kotlin.multiplatform).apply(false)
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }

    val kvalidVersion: String by project

    group = "io.github.mrkekovich"
    version = kvalidVersion
}