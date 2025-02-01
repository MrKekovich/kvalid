package io.github.kverify.core.exception

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolation
import kotlin.jvm.JvmName

/**
 * An exception thrown when validation rules are violated.
 *
 * This exception can hold one or more violations, providing details about
 * why the validation failed. It also supports custom messages and causes.
 *
 * @property violations A list of violations describing validation failures.
 * @property message An optional custom exception message.
 * @property cause The underlying cause of the exception, if any.
 */
open class ValidationException(
    override val message: String? = null,
    val violations: List<Violation> = emptyList(),
    override val cause: Throwable? = null,
) : Throwable()

/**
 * Creates a [ValidationException] from the provided [message], [violationMessages], and [cause].
 *
 * This factory function constructs a [ValidationException] by converting the [violationMessages]
 * from a list of strings to [Violation] objects, and optionally includes a [cause].
 *
 * @param message The optional custom message for the exception.
 * @param violationMessages A list of violation messages describing the validation failures.
 * @param cause The underlying cause of the exception, if any.
 * @return A [ValidationException] instance with the specified violation messages and cause.
 */
fun ValidationException(
    message: String? = null,
    violationMessages: List<String> = emptyList(),
    cause: Throwable? = null,
): ValidationException =
    ValidationException(
        message,
        violationMessages.map { it.asViolation() },
        cause,
    )

/**
 * Creates a [ValidationException] with the provided [violations] and [cause].
 *
 * This factory function constructs a [ValidationException] instance, automatically generating
 * a descriptive message based on the provided [violations]. If [violations] is empty,
 * a generic "Validation failed" message is used.
 *
 * @param violations A list of violations describing the validation failures.
 * @param cause The underlying cause of the exception, if any.
 * @return A [ValidationException] instance with the specified violations and cause.
 */
fun ValidationException(
    violations: List<Violation>,
    cause: Throwable? = null,
): ValidationException {
    val message =
        if (violations.isEmpty()) {
            "Validation failed"
        } else {
            "Validation failed: ${violations.joinToString(", ") { it.message }}"
        }

    return ValidationException(
        violations = violations,
        message = message,
        cause = cause,
    )
}

/**
 * Creates a [ValidationException] with the provided [violations] and [cause].
 *
 * This factory function constructs a [ValidationException] instance, automatically generating
 * a descriptive message based on the provided [violations]. If [violations] is empty,
 * a generic "Validation failed" message is used.
 *
 * @param violations A list of violations describing the validation failures.
 * @param cause The underlying cause of the exception, if any.
 * @return A [ValidationException] instance with the specified violations and cause.
 */
fun ValidationException(
    vararg violations: Violation,
    cause: Throwable? = null,
): ValidationException =
    ValidationException(
        violations.asList(),
        cause,
    )

/**
 * Creates a [ValidationException] from the provided [violationMessages] and [cause].
 *
 * This factory function converts the given [violationMessages] from strings to [Violation]
 * instances and constructs a [ValidationException]. If no message is provided, a generic
 * message will be generated.
 *
 * @param violationMessages A list of violation messages describing the validation failures.
 * @param cause The underlying cause of the exception, if any.
 * @return A [ValidationException] instance with the provided violation messages and cause.
 */
@JvmName("ValidationExceptionString")
fun ValidationException(
    violationMessages: List<String> = emptyList(),
    cause: Throwable? = null,
): ValidationException =
    ValidationException(
        violationMessages.map { it.asViolation() },
        cause,
    )
