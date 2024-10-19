package io.github.kverify.core.validator

import io.github.kverify.core.context.Predicate
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.exception.ValidationException

/**
 * An implementation of KVerifyContext that immediately throws exceptions for validation failures.
 */
object ThrowingValidator : ValidationContext {
    /**
     * Executes the given validation block.
     *
     * @param block The validation block to execute.
     */
    inline operator fun invoke(block: ThrowingValidator.() -> Unit): Unit = run(block)

    /**
     * Throws a [ValidationException] with the given [message] if the given [predicate] evaluates to `false`.
     *
     * @param message The failure message.
     * @param predicate The predicate to validate the value.
     * @throws ValidationException with the given [message] if the [predicate] evaluates to `false`.
     */
    override fun validate(
        message: String,
        predicate: Predicate,
    ) {
        if (!predicate()) {
            throw ValidationException(message)
        }
    }
}
