package io.github.kverify.core.validator

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.exception.ValidationException

/**
 * A [ValidationContext] implementation that throws a [ValidationException]
 * on the first validation failure.
 *
 * This validator immediately interrupts the validation process if a failure
 * occurs, throwing [ValidationException] with the specified failure message. It is
 * useful in scenarios where validation errors should halt execution.
 */
open class ThrowingValidator : ValidationContext {
    /**
     * Throws a [ValidationException] with the given [message].
     *
     * This method interrupts the validation process and provides
     * an exception describing the validation failure.
     *
     * @param message The failure message to include in the exception.
     * @throws ValidationException Always thrown with the provided [message].
     */
    override fun onFailure(message: String): Nothing = throw ValidationException(listOf(message))
}
