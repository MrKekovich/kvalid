package io.github.kverify.core.model

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.violation.Violation
import kotlin.jvm.JvmInline

/**
 * Represents the result of a validation process.
 *
 * This class encapsulates a list of violations and provides utility
 * functions to check validity, combine validation results, and produce string representations.
 *
 * @property violations A list of violations describing validation failures.
 */
@JvmInline
value class ValidationResult(
    val violations: List<Violation>,
) {
    /**
     * Indicates whether the validation result is valid (i.e., no violations are present).
     */
    inline val isValid: Boolean
        get() = violations.isEmpty()

    /**
     * Indicates whether the validation result is invalid (i.e., at least one violation is present).
     */
    inline val isInvalid: Boolean
        get() = violations.isNotEmpty()

    /**
     * Combines this validation result with another validation result.
     *
     * The violation messages from both results are merged into a new instance.
     *
     * @param other The other [ValidationResult] to combine with.
     * @return A new [ValidationResult] containing violation messages from both results.
     */
    operator fun plus(other: ValidationResult): ValidationResult =
        ValidationResult(
            violations + other.violations,
        )

    /**
     * Combines this validation result with a list of other validation results.
     *
     * All violation messages are merged into a new instance.
     *
     * @param results A list of [ValidationResult] instances to combine with.
     * @return A new [ValidationResult] containing violation messages from all results.
     */
    operator fun plus(results: List<ValidationResult>): ValidationResult =
        ValidationResult(
            violations + results.flatMap { it.violations },
        )

    /**
     * Returns a string representation of the validation result.
     *
     * @return A string indicating whether the validation is valid or invalid, along with violations if any.
     */
    override fun toString(): String =
        if (isValid) {
            "ValidationResult(valid=true)"
        } else {
            "ValidationResult(valid=false, violations=$violations)"
        }

    companion object {
        /**
         * An instance representing a valid validation result.
         */
        val VALID = ValidationResult(emptyList())
    }
}

/**
 * Creates a [ValidationResult] from one or more violations.
 *
 * @param violations The violations to include in the validation result.
 * @return A new [ValidationResult] containing the provided violations.
 */
inline fun ValidationResult(vararg violations: Violation): ValidationResult =
    ValidationResult(
        violations.asList(),
    )

/**
 * Throws a [ValidationException] if [ValidationResult] is invalid.
 *
 * Constructs a detailed exception message by joining the [ValidationResult.violationMessages]
 * using the provided parameters. If [ValidationResult] is valid, this function does nothing.
 *
 * @param separator A sequence used to separate violation messages in the exception message.
 * @param prefix A prefix to prepend to the exception message.
 * @param postfix A postfix to append to the exception message.
 * @param limit The maximum number of violation messages to include in the exception message.
 * Defaults to `-1` (no limit).
 * @param truncated A string to indicate truncation if the limit is exceeded.
 * @param cause An optional underlying cause for the exception.
 * @param transform An optional transformation applied to each violation message before joining.
 * @throws ValidationException if [ValidationResult] is invalid.
 */
@Suppress("LongParameterList")
fun ValidationResult.throwOnFailure(
    separator: CharSequence = ", ",
    prefix: CharSequence = "Validation failed: [",
    postfix: CharSequence = "]",
    limit: Int = -1,
    truncated: CharSequence = "...",
    cause: Throwable? = null,
    transform: (Violation) -> String = { it.message },
) {
    if (isValid) return

    throw ValidationException(
        message =
            violations.joinToString(
                separator = separator,
                prefix = prefix,
                postfix = postfix,
                limit = limit,
                truncated = truncated,
                transform = transform,
            ),
        violations = violations,
        cause = cause,
    )
}

/**
 * Executes the given [block] if [ValidationResult] is valid.
 *
 * @param block The code block to execute if [ValidationResult] is valid.
 * @return [ValidationResult], unchanged.
 */
inline fun ValidationResult.onValid(block: () -> Unit): ValidationResult {
    if (isValid) block()
    return this
}

/**
 * Executes the given [block] if [ValidationResult] is invalid.
 *
 * @param block The code block to execute, receiving the list of violation messages.
 * @return [ValidationResult], unchanged.
 */
inline fun ValidationResult.onInvalid(block: (List<Violation>) -> Unit): ValidationResult {
    if (isInvalid) block(violations)
    return this
}

/**
 * Applies one of the provided functions based on the validity of [ValidationResult].
 *
 * Executes [onValid] if [ValidationResult] is valid, or [onInvalid] otherwise.
 *
 * @param T The return type of the provided functions.
 * @param onValid The function to execute if [ValidationResult] is valid.
 * @param onInvalid The function to execute if [ValidationResult] is invalid.
 * @return The result of either [onValid] or [onInvalid], depending on the validity of [ValidationResult].
 */
inline fun <T> ValidationResult.fold(
    onValid: () -> T,
    onInvalid: (List<Violation>) -> T,
): T =
    if (isValid) {
        onValid()
    } else {
        onInvalid(violations)
    }

/**
 * Converts [ValidationResult] into a [ValidationException], or returns `null` if it is valid.
 *
 * Constructs the exception using the violation messages, an optional custom message, and cause.
 *
 * @param message An optional custom exception message.
 * @param cause An optional underlying cause for the exception.
 * @return A [ValidationException] if this [ValidationResult] is invalid, or `null` if it is valid.
 */
inline fun ValidationResult.asExceptionOrNull(
    message: String? = null,
    cause: Throwable? = null,
): ValidationException? =
    fold(
        onValid = { null },
        onInvalid = {
            ValidationException(
                message = message,
                violations = it,
                cause = cause,
            )
        },
    )

/**
 * Converts this [ValidationResult] into a [ValidationException], or returns `null` if it is valid.
 *
 * Constructs the exception using the violation messages and an optional cause.
 *
 * @param cause An optional underlying cause for the exception.
 * @return A [ValidationException] if this [ValidationResult] is invalid, or `null` if it is valid.
 */
inline fun ValidationResult.asExceptionOrNull(cause: Throwable? = null): ValidationException? =
    fold(
        onValid = { null },
        onInvalid = {
            ValidationException(
                violations = it,
                cause = cause,
            )
        },
    )
