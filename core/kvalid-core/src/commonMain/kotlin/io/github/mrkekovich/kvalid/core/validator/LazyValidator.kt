package io.github.mrkekovich.kvalid.core.validator

import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.model.Rule

/**
 * Stores validation rules.
 */
open class LazyValidator : KValidContext {
    private val _rules = mutableListOf<Rule>()

    val rules: List<Rule> get() = _rules

    override fun validate(rule: Rule) {
        _rules.add(rule)
    }
}
