package io.github.mrkekovich.kvalid.core.strategy

import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.context.ValidationPredicate
import io.github.mrkekovich.kvalid.core.dto.ValidationRule
import io.github.mrkekovich.kvalid.core.exception.ValidationException

class LazyContext : KValidContext {
    private val _rules = mutableListOf<ValidationRule<*>>()

    val rules: List<ValidationRule<*>>
        get() = _rules

    override fun <T> T.validate(message: String, predicate: ValidationPredicate<T>): T {
        _rules.add(ValidationRule(this, message, predicate))
        return this
    }

    override fun rule(message: String, predicate: () -> Boolean): ValidationRule<Unit> =
        ValidationRule(Unit, message) { predicate() }.also {
            _rules.add(it)
        }

    fun validate(): List<ValidationException> = rules
        .filter { !it.validate() }
        .map { ValidationException(it.message) }
}
