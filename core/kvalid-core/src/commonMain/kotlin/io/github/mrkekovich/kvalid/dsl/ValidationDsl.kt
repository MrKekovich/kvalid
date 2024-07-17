package io.github.mrkekovich.kvalid.dsl

import io.github.mrkekovich.kvalid.core.annotation.KValidDslMarker
import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.dto.NamedValue
import io.github.mrkekovich.kvalid.core.dto.ValidationResult
import io.github.mrkekovich.kvalid.core.exception.ValidationException
import io.github.mrkekovich.kvalid.core.strategy.AggregateValidationContext
import io.github.mrkekovich.kvalid.core.strategy.ImmediateValidationContext
import io.github.mrkekovich.kvalid.core.strategy.ValidationStrategy

@KValidDslMarker
inline fun validate(
    strategy: ValidationStrategy = ValidationStrategy.Aggregate,
    block: KValidContext.() -> Unit,
): ValidationResult =
    when (strategy) {
        ValidationStrategy.Aggregate -> aggregateValidate(block)
        ValidationStrategy.Immediate -> immediateValidate(block)
    }

@KValidDslMarker
inline fun aggregateValidate(block: AggregateValidationContext.() -> Unit): ValidationResult {
    val violations = AggregateValidationContext().apply(block).violations

    return when {
        violations.isEmpty() -> ValidationResult.valid()
        else -> ValidationResult.invalid(violations)
    }
}

@KValidDslMarker
inline fun immediateValidate(block: ImmediateValidationContext.() -> Unit): ValidationResult = try {
    ImmediateValidationContext().apply(block)
    ValidationResult.valid()
} catch (e: ValidationException) {
    ValidationResult.invalid(e)
}

infix fun <T> T.withName(name: String): NamedValue<T> = NamedValue(name, this)
