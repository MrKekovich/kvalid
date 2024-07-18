package io.github.mrkekovich.kvalid.core.validator

import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.context.Predicate
import io.github.mrkekovich.kvalid.core.context.ValuePredicate
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

    override fun rule(message: String, predicate: Predicate): Rule = Rule(message, predicate).also {
        _rules.add(it)
    }

    /**
     * Adds a validation rule to be evaluated later.
     *
     * @param rule The Rule to add.
     */
    override fun rule(rule: Rule) {
        _rules.add(rule)
    }

    fun <T> lazyRule(message: String, value: T, predicate: ValuePredicate<T>): Rule = rule(message) { predicate(value) }

    override fun violation(message: String) {
        rule(message) { false }
    }

    override fun <T> T.validate(message: String, predicate: ValuePredicate<T>): T {
        lazyRule(message, this, predicate)
        return this
    }
}
