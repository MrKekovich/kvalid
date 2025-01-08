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
fun <T> createRule(predicate: ValidationContext.(T) -> Unit): Rule<T> = Rule(predicate)

/**
 * Creates a [Rule] that can be used with a [NamedValue] of type [T].
 *
 * Provides a [ValidationContext] and a [NamedValue] as the lambda parameter.
 *
 * @param T The type of the value wrapped in [NamedValue]
 * @param predicate The validation logic to execute
 * @return A new [Rule] based on the given [predicate]
 */
fun <T> createNamedRule(predicate: ValidationContext.(NamedValue<T>) -> Unit): Rule<NamedValue<T>> = Rule(predicate)

/**
 * Creates a [Rule] that will execute [ValidationContext.validate] with the given [message]
 * and [predicate].
 *
 * @param T The type of the value passed to [predicate]
 * @param message The failure message to use
 * @param predicate The predicate to validate the value
 * @return A new [Rule] that performs validation
 */
fun <T> createRule(
    message: String,
    predicate: (T) -> Boolean,
): Rule<T> =
    Rule {
        validate(message) { predicate(it) }
    }

/**
 * Creates a [Rule] that will execute [ValidationContext.validate] with the given [message]
 * and [predicate].
 *
 * This rule does not take any value.
 *
 * @param message The failure message to use
 * @param predicate The predicate to validate
 * @return A new [Rule] for unit validation
 */
fun createUnitRule(
    message: String,
    predicate: () -> Boolean,
): Rule<Unit> =
    Rule {
        validate(message) { predicate() }
    }
