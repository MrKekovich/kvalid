apply(plugin = "signing")

val kverifyVersion: String by project

fun isReleaseBuild(): Boolean =
    !kverifyVersion.toString().endsWith("SNAPSHOT")

fun getProperty(variableName: String): String =
    findProperty(variableName)?.toString()
        ?: System.getenv(variableName)
        ?: run {
            println("!!! WARNING: Missing $variableName. Using default value.")
            ""
        }

if (isReleaseBuild()) {
    configure<SigningExtension> {
        useInMemoryPgpKeys(
            getProperty("SIGNING_KEY_ID"),
            getProperty("SIGNING_KEY"),
            getProperty("SIGNING_PASSWORD"),
        )

        sign(extensions.getByType<PublishingExtension>().publications)
    }
}
