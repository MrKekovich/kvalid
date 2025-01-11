package io.github.kverify.dsl.extension

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
