val groupId: String by project
val kverifyVersion: String by project

plugins {
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.maven.publish)
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }

    group = groupId
    version = kverifyVersion
}
