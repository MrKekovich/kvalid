package io.github.kverify.dsl.model

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.core.violation.Violation
import io.github.kverify.dsl.extension.validate

/**
 * Creates a [Rule] that can be used with a value of type [T].
 *
 * Provides a [ValidationContext] for validation logic.
 *
 * @param T The type of the value passed to [predicate].
 * @param predicate The validation logic to execute.
 * @return A new [Rule] based on the given [predicate].
 */
fun <T> createRule(predicate: ValidationContext.(T) -> Unit): Rule<T> =
    Rule(
        predicate,
    )

/**
 * Creates a [Rule] that can be used with a [NamedValue] of type [T].
 *
 * Provides a [ValidationContext] and a [NamedValue] as the lambda parameter.
 *
 * @param T The type of the value wrapped in [NamedValue].
 * @param predicate The validation logic to execute.
 * @return A new [Rule] based on the given [predicate].
 */
fun <T> createNamedRule(predicate: ValidationContext.(NamedValue<T>) -> Unit): Rule<NamedValue<T>> =
    Rule(
        predicate,
    )

/**
 * Creates a [Rule] that will execute [ValidationContext.validate] with the given [violation]
 * and [predicate].
 *
 * @param T The type of the value passed to [predicate].
 * @param violation The violation to use if the validation fails.
 * @param predicate The predicate to validate the value.
 * @return A new [Rule] that performs validation.
 */
inline fun <T> createRule(
    violation: Violation,
    crossinline predicate: (T) -> Boolean,
): Rule<T> =
    Rule {
        validate(predicate(it)) { violation }
    }

/**
 * Creates a [Rule] that validates a value using a fixed [condition] and a lazy violation generator.
 *
 * The returned rule uses the [ValidationContext.validate] function to check if the [condition]
 * is `true`. If it is `false`, the [lazyViolation] lambda is invoked to generate a [Violation] for the given value.
 *
 * @param T The type of the value being validated.
 * @param condition The condition to validate.
 * @param lazyViolation A lambda that generates a [Violation] based on the value.
 * @return A new [Rule] that performs validation with the specified [condition].
 */
inline fun <T> createRule(
    condition: Boolean,
    crossinline lazyViolation: (T) -> Violation,
): Rule<T> =
    Rule {
        validate(condition) { lazyViolation(it) }
    }

/**
 * Creates a [Rule] that validates a value using the provided [predicate] and generates a [Violation]
 * with [lazyViolation] if the validation fails.
 *
 * @param T The type of the value being validated.
 * @param predicate A lambda that takes the value and returns `true` if the validation passes, or `false` otherwise.
 * @param lazyViolation A lambda that takes the value and generates the failure message.
 * @return A [Rule] to validate the given value.
 */
inline fun <T> createRule(
    crossinline predicate: (T) -> Boolean,
    crossinline lazyViolation: (T) -> Violation,
): Rule<T> =
    Rule {
        validate(predicate(it)) { lazyViolation(it) }
    }

/**
 * Creates a [Rule] that validates a fixed [condition] without any input value.
 * If the condition is `false`, the rule generates the [Violation] using [lazyViolation].
 *
 * @param condition The condition to validate.
 * @param lazyViolation A lambda that generates the failure message.
 * @return A [Rule] to validate the condition.
 */
inline fun createUnitRule(
    condition: Boolean,
    crossinline lazyViolation: () -> Violation,
): Rule<Unit> =
    Rule {
        validate(condition) { lazyViolation() }
    }
