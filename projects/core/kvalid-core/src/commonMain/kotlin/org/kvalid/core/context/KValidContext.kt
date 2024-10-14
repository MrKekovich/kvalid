package org.kvalid.core.context

import org.kvalid.core.model.Rule

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
    fun rule(
        message: String,
        predicate: Predicate,
    ): Rule =
        Rule(message, predicate).also {
            validate(it)
        }

    /**
     * Uses [validate] with [Rule], that has specified [message] and always returns `false` on [Rule.validate].
     *
     * @param message the failure message.
     * @see Rule.failure
     * @see validate
     */
    fun violation(message: String) {
        validate(Rule.failure(message))
    }
}
