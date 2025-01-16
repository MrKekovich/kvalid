package io.github.kverify.dsl.extension

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.violation.Violation

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
