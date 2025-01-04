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
class ValidationException(
    val violationMessages: List<String> = emptyList(),
    override val message: String? = null,
    override val cause: Throwable? = null,
) : Throwable() {
    constructor(
        violationMessages: List<String>,
        cause: Throwable? = null,
    ) : this(
        violationMessages = violationMessages,
        message =
            buildString {
                append("Validation failed")
                if (violationMessages.isNotEmpty()) {
                    append(": ${violationMessages.joinToString(", ")}")
                }
            },
        cause = cause,
    )

    constructor(
        message: String,
        cause: Throwable? = null,
    ) : this(
        violationMessages = emptyList(),
        message = message,
        cause = cause,
    )
}
