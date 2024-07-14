package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.annotation.KValidDslMarker
import io.github.mrkekovich.kvalid.core.dto.ValidationRule

interface KValidContext :
    CollectionValidationContext,
    ComparableValidationContext,
    StringValidationContext,
    ValidationContext {
    @KValidDslMarker
    fun rule(
        message: String,
        predicate: () -> Boolean,
    ): ValidationRule<Unit>
}
