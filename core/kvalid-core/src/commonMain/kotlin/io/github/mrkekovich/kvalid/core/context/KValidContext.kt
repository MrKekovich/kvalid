package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.model.Rule

interface KValidContext :
    NamedCollectionValidationContext,
    NamedComparableValidationContext,
    NamedStringValidationContext,
    ValidationContext {
    /**
     * Create a new [Rule] instance with the given [message] and [predicate] and validate it.
     *
     * @param message
     * @param predicate
     * @return Created [Rule]
     */
    fun rule(message: String, predicate: Predicate): Rule = Rule(message, predicate).also {
        validate(it)
    }

    /**
     * @param message the failure message.
     */
    fun violation(message: String) {
        validate(Rule.failure(message))
    }
}
