package io.github.kverify.dsl.extension

import io.github.kverify.core.context.ValidationContext

/**
 * Triggers validation failure using the given [message].
 *
 * This function invokes [ValidationContext.validate] with `false` as the validation predicate,
 * delegating the handling of the failure to the specific [ValidationContext] implementation.
 *
 * @param message The failure message
 */
fun ValidationContext.violation(message: String) {
    validate(message) { false }
}
