package io.github.kverify.dsl.extension

import io.github.kverify.core.exception.ValidationException

/**
 * Throws `this` [ValidationException] if it is not `null`.
 *
 * If the exception is null, this function does nothing.
 */
fun ValidationException?.throwOnFailure() {
    if (this != null) throw this
}

/**
 * Executes the given [block] if `this` [ValidationException] is `null`.
 *
 * This is a convenient way to perform an action only when no validation failures have occurred.
 *
 * @param block The block to execute if the [ValidationException] is `null`
 * @return The original [ValidationException], unchanged
 */
inline fun ValidationException?.onValid(block: () -> Unit): ValidationException? {
    if (this == null) block()
    return this
}

/**
 * Executes the given [block] if `this` [ValidationException] is not `null`.
 *
 * This allows handling validation failures when an exception is present.
 *
 * @param block The block to execute if the [ValidationException] is not `null`
 * @return The original [ValidationException], unchanged
 */
inline fun ValidationException?.onInvalid(block: (ValidationException) -> Unit): ValidationException? {
    if (this != null) block(this)
    return this
}

/**
 * Applies one of the given functions depending on whether `this` [ValidationException] is `null` or not.
 *
 * If the exception is `null`, the [onValid] function is executed. If the exception is not `null`,
 * the [onInvalid] function is executed with the exception as its parameter.
 *
 * @param T The return type of the [onValid] and [onInvalid] functions
 * @param onValid The function to execute if the [ValidationException] is `null`
 * @param onInvalid The function to execute if the [ValidationException] is not `null`
 * @return The result of applying either [onValid] or [onInvalid]
 */
inline fun <T> ValidationException?.fold(
    onValid: () -> T,
    onInvalid: (ValidationException) -> T,
): T =
    if (this == null) {
        onValid()
    } else {
        onInvalid(this)
    }
