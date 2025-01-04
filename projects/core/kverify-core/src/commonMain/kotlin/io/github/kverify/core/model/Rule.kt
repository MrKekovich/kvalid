package io.github.kverify.core.model

import io.github.kverify.core.context.ValidationContext

/**
 * Represents a validation rule that can be applied to a value of type [T].
 *
 * This functional interface defines the contract for validating a value within a
 * [ValidationContext].
 *
 * @param T The type of the value to be validated
 */
fun interface Rule<T> {
    /**
     * Validates the given [value] within the [ValidationContext].
     *
     * This method is invoked to perform the actual validation logic for the rule.
     *
     * @param value The value to validate
     */
    fun ValidationContext.validate(value: T)

    /**
     * Combines this [Rule] with another [Rule], creating a new composite [Rule].
     *
     * The resulting [Rule] validates the given value using both rules sequentially.
     * If either rule fails, the combined validation is considered unsuccessful.
     *
     * @param other The other [Rule] to combine with this one
     * @return A new [Rule] that applies both validations
     */
    operator fun plus(other: Rule<T>): Rule<T> =
        Rule {
            this.run { validate(it) }
            other.run { validate(it) }
        }
}
