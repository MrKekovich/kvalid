package io.github.mrkekovich.kvalid.core.strategy

import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.context.ValidationPredicate
import io.github.mrkekovich.kvalid.core.dto.NamedValue
import io.github.mrkekovich.kvalid.core.dto.ValidationRule
import io.github.mrkekovich.kvalid.core.exception.ValidationException

class FailFastContext : KValidContext {
    override fun <T> NamedValue<T>.validate(message: String, predicate: ValidationPredicate<T>): NamedValue<T> {
        if (!predicate(value)) throw ValidationException(message)
        return this
    }

    override fun rule(message: String, predicate: () -> Boolean): ValidationRule<Unit> =
        ValidationRule(Unit, message) { predicate() }.also {
            if (!predicate()) throw ValidationException(message)
        }
}
