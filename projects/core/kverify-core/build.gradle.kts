import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotest.multiplatform)
    alias(libs.plugins.maven.publish)
    id("maven-publish")
    id("signing")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
    }

    js(IR) {
        nodejs()
        browser()
        binaries.executable()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosX64()
    macosArm64()
    watchosArm32()
    watchosArm64()
    watchosSimulatorArm64()
    watchosX64()
    tvosArm64()
    tvosSimulatorArm64()
    tvosX64()
    mingwX64()
    linuxX64()
    linuxArm64()

    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotest.framework.engine)
            implementation(libs.kotest.property)
            implementation(kotlin("test"))
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
        }

        jvmMain.dependencies {
            implementation(kotlin("reflect"))
        }

        jvmTest.dependencies {
            implementation(libs.kotest.runner.junit5)
        }
    }
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
    filter {
        isFailOnNoMatchingTests = false
    }
    testLogging {
        showExceptions = true
        showStandardStreams = true
        events =
            setOf(
                org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
            )
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

mavenPublishing {
    val groupId: String by project
    val kverifyVersion: String by project

    coordinates(
        groupId = "io.github.kverify",
        artifactId = "kverify-core",
        version = kverifyVersion,
    )

    pom {
        name.set("KVerify")
        description.set("KVerify - Kotlin Validation Library")
        inceptionYear.set("2024")
        url.set("https://github.com/kverify/kverify")

        licenses {
            license {
                name.set("The Apache Software License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("MrKekovich")
                name.set("MrKekovich")
                email.set("mrkekovich.official@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/kverify/kverify")
        }
    }
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
