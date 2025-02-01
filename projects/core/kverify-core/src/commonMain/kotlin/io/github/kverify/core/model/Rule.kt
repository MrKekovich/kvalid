package io.github.kverify.core.model

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.violation.Violation

typealias NamedRule<T> = Rule<NamedValue<T>>

fun interface Rule<T> {
    fun ValidationContext.runValidation(value: T)
}

fun <T> createRule(predicate: ValidationContext.(T) -> Unit): Rule<T> =
    Rule(
        predicate,
    )

inline fun <T> createRule(
    violation: Violation,
    crossinline predicate: (T) -> Boolean,
): Rule<T> =
    Rule {
        validate(predicate(it)) { violation }
    }

inline fun <T> createRule(
    condition: Boolean,
    crossinline lazyViolation: (T) -> Violation,
): Rule<T> =
    Rule {
        validate(condition) { lazyViolation(it) }
    }

inline fun <T> createRule(
    crossinline predicate: (T) -> Boolean,
    crossinline lazyViolation: (T) -> Violation,
): Rule<T> =
    Rule {
        validate(predicate(it)) { lazyViolation(it) }
    }

fun <T> createNamedRule(predicate: ValidationContext.(NamedValue<T>) -> Unit): NamedRule<T> =
    Rule(
        predicate,
    )

inline fun createUnitRule(
    condition: Boolean,
    crossinline lazyViolation: () -> Violation,
): Rule<Unit> =
    Rule {
        validate(condition) { lazyViolation() }
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
