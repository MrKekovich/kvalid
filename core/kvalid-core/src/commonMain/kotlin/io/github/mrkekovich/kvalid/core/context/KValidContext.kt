package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.model.Rule

interface KValidContext :
    NamedCollectionValidationContext,
    NamedComparableValidationContext,
    NamedStringValidationContext,
    ValidationContext {
    /**
     * Add validation rule
     *
     * @param message the failure message
     * @param predicate the validation predicate
     * @return [Rule] with specified message and predicate
     */
    fun rule(
        message: String,
        predicate: () -> Boolean,
    ): Rule

    /**
     * Interact with the given [Rule]
     *
     * @param rule The validation rule to be added.
     */
    fun rule(rule: Rule)

    /**
     * @param message the failure message
     */
    fun violation(message: String)
}
