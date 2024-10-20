apply(plugin = "maven-publish")

configure<PublishingExtension> {
    publications {
        withType<MavenPublication> {
            pom {
                name.set("KVerify")
                description.set("KVerify - Kotlin multiplatform validation library")
                url.set("https://insert-koin.io/")
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                scm {
                    url.set("https://github.com/kverify/kverify")
                    connection.set("https://github.com/kverify/kverify.git")
                }
                developers {
                    developer {
                        name.set("MrKekovich")
                        email.set("mrkekovich.official@gmail.com")
                    }
                }
            }
        }
    }
}

apply(from = file("../../gradle/signing.gradle.kts"))