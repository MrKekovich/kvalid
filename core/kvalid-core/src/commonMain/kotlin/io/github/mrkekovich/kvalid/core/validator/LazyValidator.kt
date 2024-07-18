package io.github.mrkekovich.kvalid.core.validator

import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.context.ValidationPredicate
import io.github.mrkekovich.kvalid.core.model.Rule
import io.github.mrkekovich.kvalid.core.exception.ValidationException

/**
 * Stores validation rules and executes them on [validate].
 */
open class LazyValidator : KValidContext {
    val result = sequence {
        for (rule in _rules) {
            if (!rule.validate()) yield(ValidationException(rule.failMessage))
        }
    }

    private val _rules = mutableListOf<Rule>()

    val rules: List<Rule> get() = _rules

    override fun rule(message: String, predicate: () -> Boolean): Rule = Rule(message, predicate).also {
        _rules.add(it)
    }

    fun <T> lazyRule(message: String, value: T, predicate: ValidationPredicate<T>): Rule = rule(message) { predicate(value) }

    override fun violation(message: String) {
        rule(message) { false }
    }

    override fun <T> T.validate(message: String, predicate: ValidationPredicate<T>): T {
        lazyRule(message, this, predicate)
        return this
    }
}