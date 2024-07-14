package io.github.mrkekovich.kvalid.dsl

import io.github.mrkekovich.kvalid.core.annotation.KValidDslMarker
import io.github.mrkekovich.kvalid.core.dto.NamedValue
import io.github.mrkekovich.kvalid.core.dto.ValidationResult
import io.github.mrkekovich.kvalid.core.exception.ValidationException
import io.github.mrkekovich.kvalid.core.strategy.CollectAllContext
import io.github.mrkekovich.kvalid.core.strategy.FailFastContext
import io.github.mrkekovich.kvalid.core.strategy.LazyContext

@KValidDslMarker
inline fun collectViolations(block: CollectAllContext.() -> Unit): ValidationResult {
    val violations = CollectAllContext().apply(block).violations

    return when {
        violations.isEmpty() -> ValidationResult.valid()
        else -> ValidationResult.invalid(violations)
    }
}

@KValidDslMarker
inline fun failFast(block: FailFastContext.() -> Unit): ValidationResult = try {
    FailFastContext().apply(block)
    ValidationResult.valid()
} catch (e: ValidationException) {
    ValidationResult.invalid(e)
}

@KValidDslMarker
inline fun lazyValidation(block: LazyContext.() -> Unit): LazyContext =
    LazyContext().apply(block)

@KValidDslMarker
infix fun <T> T.named(name: String): NamedValue<T> = NamedValue(name, this)
