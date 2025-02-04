package io.github.kverify.core.model

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.violation.Violation

/**
 * Represents a validation rule for a value of type [T].
 */
fun interface Rule<T> {
    /**
     * Runs validation for the given [value] within this [ValidationContext].
     */
    fun ValidationContext.runValidation(value: T)
}

/**
 * A validation [Rule] for a [NamedValue].
 */
typealias NamedRule<T> = Rule<NamedValue<T>>

/**
 * Creates a [Rule] using the given [block].
 */
fun <T> createRule(block: ValidationContext.(T) -> Unit): Rule<T> = Rule(block)

/**
 * Creates a [Rule] that executes [ValidationContext.validate]
 * with the given [predicate] and [violationGenerator].
 */
inline fun <T> createRule(
    crossinline predicate: (T) -> Boolean,
    crossinline violationGenerator: (T) -> Violation,
): Rule<T> =
    Rule {
        validate(
            predicate(it),
        ) {
            violationGenerator(it)
        }
    }

/**
 * Combines this rule with [other], applying both sequentially.
 */
operator fun <T> Rule<T>.plus(other: Rule<T>): Rule<T> =
    Rule validationContext@{ value ->
        this@plus.runValidation(
            this@validationContext,
            value,
        )

        other.runValidation(
            this@validationContext,
            value,
        )
    }

/**
 * Runs this rule within the given [context] for the specified [value].
 */
inline fun <T> Rule<T>.runValidation(
    context: ValidationContext,
    value: T,
): Unit = context.runValidation(value)
