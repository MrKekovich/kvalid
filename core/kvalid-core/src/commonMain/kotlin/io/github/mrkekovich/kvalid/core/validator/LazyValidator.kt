package io.github.mrkekovich.kvalid.core.validator

import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.model.Rule

/**
 * An implementation of KValidContext that lazily evaluates validation rules.
 */
open class LazyValidator : KValidContext {
    /**
     * The internal list of validation rules.
     */
    private val _rules = mutableListOf<Rule>()

    /**
     * A read-only view of the validation rules.
     */
    val rules: List<Rule> get() = _rules.toList()

    /**
     * Adds a [rule] to the list of validation [rules].
     *
     * @param rule The rule to add.
     */
    override fun validate(rule: Rule) {
        _rules.add(rule)
    }
}
