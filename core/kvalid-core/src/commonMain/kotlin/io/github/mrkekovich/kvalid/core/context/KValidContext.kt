package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.annotation.KValidDslMarker
import io.github.mrkekovich.kvalid.core.dto.ValidationRule

@KValidDslMarker
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
     * @return the validation rule with [Unit] as [ValidationRule.value]
     */
    fun rule(
        message: String,
        predicate: () -> Boolean,
    ): ValidationRule<Unit>

    /**
     * @param message the failure message
     */
    fun violation(message: String)
}
