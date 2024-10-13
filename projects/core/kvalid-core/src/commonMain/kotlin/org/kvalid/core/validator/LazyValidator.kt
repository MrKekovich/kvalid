package org.kvalid.core.validator

import org.kvalid.core.context.KValidContext
import org.kvalid.core.context.Predicate
import org.kvalid.core.model.Rule

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
     * Adds a new [Rule] with the given [message] and [predicate] to the [rules].
     *
     * @param message The failure message that will be used in the [Rule] creation.
     * @param predicate The predicate that will be used in the [Rule] creation.
     */
    override fun validate(message: String, predicate: Predicate) {
        validate(Rule(message, predicate))
    }

    /**
     * Adds a [rule] to the list of validation [rules].
     *
     * @param rule The rule to add.
     */
    override fun validate(rule: Rule) {
        _rules.add(rule)
    }
}
