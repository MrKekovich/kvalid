package io.github.kverify.dsl.model

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule

/**
 * Create rule that will be compatible with values of type [T]
 *
 * @param T The type of the value that will be passed to [predicate]
 * @see ValidationContext.validate
 */
fun <T> createRule(predicate: ValidationContext.(T) -> Unit): Rule<T> =
    Rule(
        predicate,
    )

/**
 * Create rule that will be compatible with [NamedValue] of type [T]
 *
 * @param T The type of the value that will be passed to [predicate] wrapped in [NamedValue]
 * @see ValidationContext.validate
 */
fun <T> createNamedRule(predicate: ValidationContext.(NamedValue<T>) -> Unit): Rule<NamedValue<T>> =
    Rule(
        predicate,
    )

/**
 * Create rule that will execute [ValidationContext.validate]
 * with the given [message] and [predicate]
 *
 * @param T The type of the value that will be passed to [predicate]
 * @param message The failure message
 * @see ValidationContext.validate
 */
fun <T> createRule(
    message: String,
    predicate: (T) -> Boolean,
): Rule<T> =
    Rule {
        validate(message) { predicate(it) }
    }

/**
 * Create rule that will execute [ValidationContext.validate]
 * with the given [message] and [predicate]
 *
 * **Does not take any value**
 *
 * @param message The failure message
 * @param predicate
 * @see ValidationContext.validate
 */
fun createRule(
    message: String,
    predicate: () -> Boolean,
): Rule<Unit> =
    Rule {
        validate(message) { predicate() }
    }
