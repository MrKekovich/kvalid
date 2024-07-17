package io.github.mrkekovich.kvalid.core.strategy

import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.context.ValidationPredicate
import io.github.mrkekovich.kvalid.core.dto.LazyValidationRule
import io.github.mrkekovich.kvalid.core.dto.Rule
import io.github.mrkekovich.kvalid.core.dto.ValidationRule
import io.github.mrkekovich.kvalid.core.exception.ValidationException

/**
 * Stores validation rules and executes them on [validate].
 */
open class LazyValidationContext : KValidContext {
    val result = sequence {
        for (rule in _rules) {
            if (!rule.validate()) yield(ValidationException(rule.failMessage))
        }
    }

    private val _rules = mutableListOf<Rule>()

    val rules: List<Rule> get() = _rules

    override fun rule(message: String, predicate: () -> Boolean): ValidationRule = ValidationRule(message, predicate).also {
        _rules.add(it)
    }

    override fun violation(message: String) {
        _rules.add(ValidationRule(message) { false })
    }

    override fun <T> T.validate(message: String, predicate: ValidationPredicate<T>): T {
        _rules.add(
            LazyValidationRule(message, this, predicate)
        )
        return this
    }
}
