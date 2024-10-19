package io.github.kverify.dsl.extension

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.validator.LazyValidator

/**
 * Uses [ValidationContext.validate] with the given [message] and `false` as the `predicate`
 *
 * This method will add failing block to [LazyValidator] **immediately**.
 *
 * @param message Failure message
 */
fun ValidationContext.violation(message: String) {
    validate(message) { false }
}
