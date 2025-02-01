package io.github.kverify.core.validator

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.Rule
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.violation.Violation
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

open class ThrowingValidator : ValidationContext {
    override fun onFailure(violation: Violation): Nothing = throw ValidationException(listOf(violation))

    @OptIn(ExperimentalContracts::class)
    fun validate(
        condition: Boolean,
        lazyViolation: () -> Violation,
    ) {
        contract {
            returns() implies condition
        }

        if (!condition) onFailure(lazyViolation())
    }
}

inline fun validateOrThrow(block: ThrowingValidator.() -> Unit) {
    ThrowingValidator().apply(block)
}

fun <T> T.validateOrThrow(vararg rules: Rule<T>): Unit =
    validateOrThrow {
        applyRules(*rules)
    }

inline fun validateFirst(block: ThrowingValidator.() -> Unit): ValidationResult =
    try {
        validateOrThrow(block)
        ValidationResult.VALID
    } catch (violation: ValidationException) {
        ValidationResult(violation.violations)
    }

fun <T> T.validateFirst(vararg rules: Rule<T>): ValidationResult =
    validateFirst {
        applyRules(*rules)
    }

inline fun <T> runValidatingFirst(block: ThrowingValidator.() -> T): Result<T> =
    try {
        Result.success(
            ThrowingValidator().run(block),
        )
    } catch (e: ValidationException) {
        Result.failure(e)
    }
