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
