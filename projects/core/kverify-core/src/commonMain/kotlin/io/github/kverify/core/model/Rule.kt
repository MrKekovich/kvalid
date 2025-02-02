package io.github.kverify.core.model

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.violation.Violation

fun interface Rule<T> {
    fun ValidationContext.runValidation(value: T)
}

typealias NamedRule<T> = Rule<NamedValue<T>>

fun <T> createRule(block: ValidationContext.(T) -> Unit): Rule<T> = Rule(block)

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

inline fun <T> Rule<T>.runValidation(
    context: ValidationContext,
    value: T,
): Unit = context.runValidation(value)

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
