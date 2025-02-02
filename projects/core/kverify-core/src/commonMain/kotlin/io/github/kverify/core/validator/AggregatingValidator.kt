package io.github.kverify.core.validator

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.Rule
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.violation.Violation

class AggregatingValidator : ValidationContext {
    val violations: MutableList<Violation> = mutableListOf()

    override fun onFailure(violation: Violation) {
        violations.add(violation)
    }
}

inline fun validateAll(block: AggregatingValidator.() -> Unit): ValidationResult =
    ValidationResult(
        AggregatingValidator().apply(block).violations,
    )

fun <T> T.validateAll(vararg rules: Rule<T>): ValidationResult =
    validateAll lambda@{
        this@validateAll.applyRules(*rules)
    }

inline fun <T> runValidatingAll(block: AggregatingValidator.() -> T): Result<T> =
    AggregatingValidator().run {
        val result = this.block()

        if (violations.isEmpty()) {
            Result.success(result)
        } else {
            Result.failure(
                ValidationException(violations),
            )
        }
    }
