package io.github.kverify.dsl.extension

import io.github.kverify.core.exception.ValidationException

/**
 * Throws the [ValidationException] if it is not null.
 *
 * This is a convenience function to check if the exception is present and throw it,
 * allowing for a more concise syntax when handling nullable [ValidationException].
 */
fun ValidationException?.throwIfPresent() {
    if (this != null) throw this
}

/**
 * Executes the given [block] if the [ValidationException] is null.
 *
 * @param block The block to execute if the [ValidationException] is null.
 * @return The original [ValidationException] unchanged.
 */
inline fun ValidationException?.onValid(block: () -> Unit): ValidationException? {
    if (this == null) block()
    return this
}

/**
 * Executes the given [block] if the [ValidationException] is not null.
 *
 * @param block The block to execute if the [ValidationException] is not null.
 * @return The original [ValidationException] unchanged.
 */
inline fun ValidationException?.onInvalid(block: (ValidationException) -> Unit): ValidationException? {
    if (this != null) block(this)
    return this
}

/**
 * Applies one of the given functions depending on whether the [ValidationException] is null or not.
 *
 * @param T The return type of the functions.
 * @param onValid The function to apply if the [ValidationException] is null.
 * @param onInvalid The function to apply if the [ValidationException] is not null.
 * @return The result of applying either the valid or invalid function.
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
