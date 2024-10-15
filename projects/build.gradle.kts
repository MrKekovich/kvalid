plugins {
    alias(libs.plugins.kotlin.multiplatform).apply(false)
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }

    val kverifyVersion: String by project

    group = "io.github.kverify"
    version = kverifyVersion
}
