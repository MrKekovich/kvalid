package io.github.kverify.core.context

import io.github.kverify.core.model.Rule

/**
 * A lambda function that evaluates a condition and returns a boolean result.
 *
 * Typically used in validation contexts to define custom validation logic.
 */
typealias Predicate = () -> Boolean

/**
 * A context for performing validation operations.
 *
 * This interface provides methods for validating individual values, named values,
 * and applying rules to ensure data integrity.
 */
interface ValidationContext {
    /**
     * Validates a condition using the provided [predicate].
     *
     * If the [predicate] evaluates to `false`, the validation fails with the specified [message].
     *
     * @param message The failure message to be associated with the validation error
     * @param predicate A lambda that evaluates a condition to determine validation success
     */
    fun validate(
        message: String,
        predicate: Predicate,
    )

    /**
     * Validates a rule that does not take any input value.
     *
     * This method runs the given [rule], which must be defined for [Unit],
     * within the current validation context.
     *
     * @param rule The rule to validate
     */
    fun validate(rule: Rule<Unit>): Unit = rule.run { validate(Unit) }

    /**
     * Validates the current value against the provided [rules].
     *
     * This method applies each rule to the value and validates it within the current
     * validation context. The original value is returned unchanged, enabling method chaining.
     *
     * @param rules A vararg array of rules to apply to the value
     * @return The original value, unchanged
     */
    fun <T> T.validate(vararg rules: Rule<T>): T {
        rules.forEach {
            it.run { validate(this@validate) }
        }

        return this
    }
}
