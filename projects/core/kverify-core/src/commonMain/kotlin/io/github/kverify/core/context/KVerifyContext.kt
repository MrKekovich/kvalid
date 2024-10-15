package io.github.kverify.core.context

import io.github.kverify.core.model.Rule

interface KVerifyContext :
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
