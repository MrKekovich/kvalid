plugins {
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.nexusPublish)
}

fun getProperty(variableName: String): String =
    findProperty(variableName)?.toString()
        ?: System.getenv(variableName)
        ?: run {
            println("!!! WARNING: Missing $variableName. Using default value.")
            ""
        }

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            username.set(getProperty("OSSRH_USERNAME"))
            password.set(getProperty("OSSRH_PASSWORD"))
        }
    }
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
