package io.github.kverify.dsl.extension

import io.github.kverify.core.context.Predicate
import io.github.kverify.core.context.ValidationContext

/**
 * Validates a condition and handles failures using a lazy failure message.
 *
 * The provided [condition] is evaluated, and if it is `false`, the [lazyFailureMessage]
 * lambda is invoked to generate a failure message, which is then passed to [onFailure].
 *
 * @param condition The condition to validate.
 * @param lazyFailureMessage A lambda to produce the failure message if the condition fails.
 */
inline fun ValidationContext.validate(
    condition: Boolean,
    lazyFailureMessage: () -> String,
) {
    if (!condition) onFailure(lazyFailureMessage())
}

/**
 * Validates a condition defined by a [predicate] and handles failures using a fixed message.
 *
 * The [predicate] is invoked to evaluate the condition, and if it returns `false`,
 * the provided [message] is passed to [onFailure].
 *
 * @param message The failure message to use if the predicate evaluates to `false`.
 * @param predicate A lambda that evaluates the condition to determine validation success.
 */
inline fun ValidationContext.validate(
    message: String,
    predicate: Predicate,
) {
    if (!predicate()) onFailure(message)
}
