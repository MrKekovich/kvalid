package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.model.NamedValue

typealias ValidationPredicate<T> = (T) -> Boolean

/**
 * Validation context. The core of all validation contexts.
 */
interface ValidationContext {

    /**
     * Validates the value against the specified predicate.
     *
     * ```
     * age.validate("Age must be at least 18") { it >= 18 }
     * ```
     *
     * @param T the type of the value
     * @param message the failure message if validation fails
     * @param predicate the predicate to validate the value
     */
    fun <T> T.validate(
        message: String,
        predicate: ValidationPredicate<T>,
    ): T

    /**
     * Validates the named value against the specified predicate, using a function to generate the failure message.
     *
     * ```
     * age.validate(
     *     message = { "${it.name} must be at least 18" },
     *     predicate = { it >= 18 }
     * )
     * ```
     *
     * @param T the type of the value
     * @param message a function that takes the name of the value and returns the failure message
     * @param predicate the predicate to validate the value
     */
    fun <T> NamedValue<T>.validate(
        message: (NamedValue<T>) -> String,
        predicate: ValidationPredicate<T>,
    ): NamedValue<T> {
        value.validate(message(this), predicate)
        return this
    }
}
