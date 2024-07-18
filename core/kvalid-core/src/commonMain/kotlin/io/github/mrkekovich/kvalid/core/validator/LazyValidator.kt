package io.github.mrkekovich.kvalid.core.validator

import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.exception.ValidationException
import io.github.mrkekovich.kvalid.core.model.Rule

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

    override fun validate(rule: Rule) {
        _rules.add(rule)
    }
}
