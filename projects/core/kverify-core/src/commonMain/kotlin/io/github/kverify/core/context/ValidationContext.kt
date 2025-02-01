package io.github.kverify.core.context

import io.github.kverify.core.model.Rule
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolation

/**
 * A lambda function that evaluates a condition and returns a boolean result.
 *
 * Typically used in validation contexts to define custom validation logic.
 */
internal typealias Predicate = () -> Boolean

/**
 * A context for performing validation operations.
 *
 * This interface provides a contract for handling validation failures.
 * Custom implementations can define specific behaviors for managing
 * failed validations, such as aggregating failure messages or
 * throwing exceptions.
 */
interface ValidationContext {
    /**
     * Handles a validation failure with the given [violation].
     *
     * Implementations define how validation failures are processed,
     * such as collecting failure messages or interrupting execution
     * by throwing an exception.
     *
     * @param violation The violation object describing why the validation failed.
     */
    fun onFailure(violation: Violation)

    /**
     * Validates a rule that does not take any input value.
     *
     * This method runs the given [rule], which must be defined for [Unit],
     * within the current validation context.
     *
     * @param rule The rule to validate
     */
    fun applyUnitRule(rule: Rule<Unit>): Unit = rule.run { validate(Unit) }

    /**
     * Validates the current value against the provided [rules].
     *
     * This method applies each rule to the value and validates it within the current
     * validation context. The original value is returned unchanged, enabling method chaining.
     *
     * @param rules A vararg array of rules to apply to the value
     * @return The original value, unchanged
     */
    fun <T> T.applyRules(vararg rules: Rule<T>): T {
        rules.forEach {
            it.run { validate(this@applyRules) }
        }

        return this
    }
}

/**
 * Validates a condition and handles failures using a lazy [Violation].
 *
 * The provided [condition] is evaluated, and if it is `false`, the [lazyViolation]
 * lambda is invoked to generate a [Violation], which is then passed to [onFailure].
 *
 * @param condition The condition to validate.
 * @param lazyViolation A lambda to produce the [Violation] if the condition fails.
 */
inline fun ValidationContext.validate(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    if (!condition) onFailure(lazyViolation())
}

/**
 * Handles a validation failure with the given [message].
 *
 * This overload allows handling failures using a simple message string.
 * It provides flexibility for scenarios where a typed violation is unnecessary.
 *
 * @param message The failure message describing why the validation failed.
 */
fun ValidationContext.onFailure(message: String): Unit = onFailure(message.asViolation())
