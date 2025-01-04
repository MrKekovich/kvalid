package io.github.kverify.core.validator

import io.github.kverify.core.context.Predicate
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.exception.ValidationException

/**
 * A [ValidationContext] implementation that throws a [ValidationException] on the first validation failure.
 *
 * This validator immediately interrupts the validation process if a failure occurs, throwing an exception
 * with the specified failure message.
 */
open class ThrowingValidator : ValidationContext {
    /**
     * Validates a condition and throws a [ValidationException] if the validation fails.
     *
     * This method evaluates the [predicate]. If the predicate fails (returns `false`), a
     * [ValidationException] is thrown containing the specified [message] as part of its
     * [ValidationException.violationMessages].
     *
     * @param message The failure message to include in the exception
     * @param predicate The predicate that determines whether the validation passes
     * @throws ValidationException If the validation fails
     */
    override fun validate(
        message: String,
        predicate: Predicate,
    ) {
        if (!predicate()) {
            throw ValidationException(listOf(message))
        }
    }
}
