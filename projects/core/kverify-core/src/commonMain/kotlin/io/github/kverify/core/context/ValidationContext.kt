package io.github.kverify.core.context

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule

/**
 * Lambda that returns a boolean value.
 */
typealias Predicate = () -> Boolean

/**
 * Validation context. An interface for validating values.
 */
interface ValidationContext {
    /**
     * Validate using the given [predicate]
     *
     * @param message failure message
     * @param predicate validation predicate
     */
    fun validate(
        message: String,
        predicate: Predicate,
    )

    /**
     * Validate using [Rule] that takes [Unit]
     *
     * @param rule validation rule
     */
    fun validate(rule: Rule<Unit>): Unit = rule.run { validate(Unit) }

    /**
     * Validate the [NamedValue] with the given [rules]
     *
     * @param rules validation rules
     * @return Unchanged [NamedValue]
     */
    fun <T> T.validate(vararg rules: Rule<T>): T {
        rules.forEach {
            it.run { validate(this@validate) }
        }

        return this
    }
}
