package io.github.kverify.core.violation

/**
 * Represents a validation violation with a descriptive message.
 *
 * Implementations of this interface can provide additional context or
 * metadata about the violation, enabling more detailed reporting or
 * handling of validation errors.
 */
interface Violation {
    /**
     * The message describing the validation violation.
     */
    val message: String
}

/**
 * Creates a [Violation] instance from the given [message].
 *
 * This is a lightweight utility function that is inlined at compile time, ensuring no
 * performance overhead when creating violations.
 *
 * @param message The message describing the validation violation.
 * @return A [Violation] instance wrapping the provided message.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Violation(message: String): Violation = AnyViolation(message)
