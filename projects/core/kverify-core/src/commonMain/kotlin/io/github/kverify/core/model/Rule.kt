package io.github.kverify.core.model

import io.github.kverify.core.context.ValidationContext

/**
 * Represents a validation rule
 *
 * @param T The type of the value
 */
fun interface Rule<T> {
    /**
     * Validate the [value] using the [ValidationContext]
     *
     * @param value The value to validate
     */
    fun ValidationContext.validate(value: T)

    /**
     * Creates new [Rule] that executes [validate] on `this` and [other].
     */
    operator fun plus(other: Rule<T>): Rule<T> =
        Rule {
            this.run { validate(it) }
            other.run { validate(it) }
        }
}
