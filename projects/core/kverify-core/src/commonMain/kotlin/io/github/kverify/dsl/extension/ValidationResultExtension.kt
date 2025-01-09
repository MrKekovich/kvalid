package io.github.kverify.dsl.extension

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.ValidationResult

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
    transform: ((String) -> CharSequence)? = null,
) {
    if (isValid) return

    throw ValidationException(
        message =
            violationMessages.joinToString(
                separator = separator,
                prefix = prefix,
                postfix = postfix,
                limit = limit,
                truncated = truncated,
                transform = transform,
            ),
        violationMessages = violationMessages,
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
inline fun ValidationResult.onInvalid(block: (List<String>) -> Unit): ValidationResult {
    if (isInvalid) block(violationMessages)
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
    onInvalid: (List<String>) -> T,
): T =
    if (isValid) {
        onValid()
    } else {
        onInvalid(violationMessages)
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
fun ValidationResult.asExceptionOrNull(
    message: String? = null,
    cause: Throwable? = null,
): ValidationException? =
    fold(
        onValid = { null },
        onInvalid = {
            ValidationException(
                message = message,
                violationMessages = it,
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
fun ValidationResult.asExceptionOrNull(cause: Throwable? = null): ValidationException? =
    fold(
        onValid = { null },
        onInvalid = {
            ValidationException(
                violationMessages = it,
                cause = cause,
            )
        },
    )
