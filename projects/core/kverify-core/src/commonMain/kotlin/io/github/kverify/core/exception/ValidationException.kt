package io.github.kverify.core.exception

/**
 * An exception thrown when validation rules are violated.
 *
 * This exception can hold one or more violation messages, providing details about
 * why the validation failed. It also supports custom messages and causes.
 *
 * @property violationMessages A list of messages describing validation failures.
 * @property message An optional custom exception message.
 * @property cause The underlying cause of the exception, if any.
 */
open class ValidationException(
    override val message: String? = null,
    val violationMessages: List<String> = emptyList(),
    override val cause: Throwable? = null,
) : Throwable()

/**
 * Creates a [ValidationException] with the provided violation messages and cause.
 *
 * This factory function constructs a [ValidationException] instance, automatically generating
 * a descriptive message based on the provided violation messages. If [violationMessages] is empty,
 * a generic "Validation failed" message is used.
 *
 * @param violationMessages A list of messages describing the validation failures.
 * @param cause The underlying cause of the exception, if any.
 * @return A [ValidationException] instance with the specified violation messages and cause.
 */
fun ValidationException(
    violationMessages: List<String>,
    cause: Throwable? = null,
): ValidationException {
    val message =
        if (violationMessages.isEmpty()) {
            "Validation failed"
        } else {
            "Validation failed: ${violationMessages.joinToString(", ")}"
        }

    return ValidationException(
        violationMessages = violationMessages,
        message = message,
        cause = cause,
    )
}
