package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.dto.ValidationRule
import io.github.mrkekovich.kvalid.core.exception.ValidationException

interface KValidContext :
    NamedCollectionValidationContext,
    NamedComparableValidationContext,
    NamedStringValidationContext,
    ValidationContext {
    /**
     * Add validation rule
     *
     * @param message the failure message
     * @param predicate the validation predicate
     * @return [ValidationRule] with specified message and predicate
     */
    fun rule(
        message: String,
        predicate: () -> Boolean,
    ): ValidationRule

    /**
     * @param message the failure message
     */
    fun violation(message: String)

    companion object : KValidContext {
        inline operator fun invoke(block: KValidContext.() -> Unit): Companion = apply(block)

        /**
         * Throws a [ValidationException] with the specified message if predicate returns false.
         *
         * @param message the failure message
         * @param predicate the validation predicate
         * @return [ValidationRule] with specified message and predicate
         * @throws ValidationException if predicate returns false
         */
        override fun rule(message: String, predicate: () -> Boolean): ValidationRule =
            ValidationRule(message) { predicate() }.also {
                if (!predicate()) throw ValidationException(message)
            }

        /**
         * Always throws a [ValidationException] with the specified message.
         *
         * @param message the failure message
         * @throws ValidationException
         */
        override fun violation(message: String): Nothing = throw ValidationException(message)

        /**
         * Throws a [ValidationException] with the specified message if predicate returns false.
         *
         * ```
         * age.validate("Age must be at least 18") { it >= 18 }
         * ```
         *
         * @param T the type of the value
         * @param message the failure message if validation fails
         * @param predicate the predicate to validate the value
         * @throws ValidationException if predicate returns false
         */
        override fun <T> T.validate(message: String, predicate: ValidationPredicate<T>): T {
            if (!predicate(this)) throw ValidationException(message)
            return this
        }
    }
}
