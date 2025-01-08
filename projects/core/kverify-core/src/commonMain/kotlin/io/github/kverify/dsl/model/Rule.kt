package io.github.kverify.dsl.model

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.dsl.extension.validate

/**
 * Creates a [Rule] that can be used with a value of type [T].
 *
 * Provides a [ValidationContext] for validation logic.
 *
 * @param T The type of the value passed to [predicate]
 * @param predicate The validation logic to execute
 * @return A new [Rule] based on the given [predicate]
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
 * @param T The type of the value wrapped in [NamedValue]
 * @param predicate The validation logic to execute
 * @return A new [Rule] based on the given [predicate]
 */
fun <T> createNamedRule(predicate: ValidationContext.(NamedValue<T>) -> Unit): Rule<NamedValue<T>> =
    Rule(
        predicate,
    )

/**
 * Creates a [Rule] that will execute [ValidationContext.applyRules] with the given [message]
 * and [predicate].
 *
 * @param T The type of the value passed to [predicate]
 * @param message The failure message to use
 * @param predicate The predicate to validate the value
 * @return A new [Rule] that performs validation
 */
inline fun <T> createRule(
    message: String,
    crossinline predicate: (T) -> Boolean,
): Rule<T> =
    Rule {
        validate(predicate(it)) { message }
    }

/**
 * Creates a [Rule] that validates a value using a fixed [condition] and a lazy failure message.
 *
 * The returned rule uses the [ValidationContext.applyRules] function to check if the [condition]
 * is `true`. If it is `false`, the [lazyFailureMessage] lambda is invoked to generate a failure
 * message for the given value.
 *
 * @param T The type of the value passed to [lazyFailureMessage].
 * @param condition The condition to validate.
 * @param lazyFailureMessage A lambda that generates a failure message based on the value.
 * @return A new [Rule] that performs validation with the specified [condition].
 */
inline fun <T> createRule(
    condition: Boolean,
    crossinline lazyFailureMessage: (T) -> String,
): Rule<T> =
    Rule {
        validate(condition) { lazyFailureMessage(it) }
    }

/**
 * Creates a [Rule] that validates a value using the provided [predicate] and generates a failure message
 * with [lazyFailureMessage] if the validation fails.
 *
 * @param T The type of the value being validated.
 * @param predicate A lambda that takes the value and returns `true` if the validation passes, or `false` otherwise.
 * @param lazyFailureMessage A lambda that takes the value and generates the failure message.
 * @return A [Rule] to validate the given value.
 */
inline fun <T> createRule(
    crossinline predicate: (T) -> Boolean,
    crossinline lazyFailureMessage: (T) -> String,
): Rule<T> =
    Rule {
        validate(predicate(it)) { lazyFailureMessage(it) }
    }

/**
 * Creates a [Rule] that validates a condition without any input value, using the provided [predicate].
 * If the validation fails, the rule uses the fixed [message] as the failure message.
 *
 * @param message The failure message to use if the validation fails.
 * @param predicate A lambda that evaluates a condition and returns `true` if valid, or `false` otherwise.
 * @return A [Rule] to validate the condition.
 */
inline fun createUnitRule(
    message: String,
    crossinline predicate: () -> Boolean,
): Rule<Unit> =
    Rule {
        validate(predicate()) { message }
    }

/**
 * Creates a [Rule] that validates a fixed [condition] without any input value.
 * If the condition is `false`, the rule generates the failure message using [lazyFailureMessage].
 *
 * @param condition The condition to validate.
 * @param lazyFailureMessage A lambda that generates the failure message.
 * @return A [Rule] to validate the condition.
 */
inline fun createUnitRule(
    condition: Boolean,
    crossinline lazyFailureMessage: () -> String,
): Rule<Unit> =
    Rule {
        validate(condition) { lazyFailureMessage() }
    }

/**
 * Creates a [Rule] that validates a condition without any input value using the provided [predicate].
 * If the validation fails, the failure message is generated using [lazyFailureMessage].
 *
 * @param predicate A lambda that evaluates a condition and returns `true` if valid, or `false` otherwise.
 * @param lazyFailureMessage A lambda that generates the failure message.
 * @return A [Rule] to validate the condition.
 */
inline fun createUnitRule(
    crossinline predicate: () -> Boolean,
    crossinline lazyFailureMessage: () -> String,
): Rule<Unit> =
    Rule {
        validate(predicate()) { lazyFailureMessage() }
    }

/**
 * Creates a [Rule] that validates a fixed [condition] without any input value.
 * If the condition is `false`, the rule uses the fixed [message] as the failure message.
 *
 * @param message The failure message to use if the validation fails.
 * @param condition The condition to validate.
 * @return A [Rule] to validate the condition.
 */
inline fun createUnitRule(
    message: String,
    condition: Boolean,
): Rule<Unit> =
    Rule {
        validate(condition) { message }
    }

/**
 * Creates a [Rule] that validates a fixed [condition] without any input value.
 * If the condition is `false`, the rule uses the fixed [message] as the failure message.
 *
 * @param condition The condition to validate.
 * @param message The failure message to use if the validation fails.
 * @return A [Rule] to validate the condition.
 */
inline fun createUnitRule(
    condition: Boolean,
    message: String,
): Rule<Unit> =
    Rule {
        validate(condition) { message }
    }
