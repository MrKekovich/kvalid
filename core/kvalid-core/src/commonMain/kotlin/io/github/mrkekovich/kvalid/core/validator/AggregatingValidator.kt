package io.github.mrkekovich.kvalid.core.validator

import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.exception.ValidationException
import io.github.mrkekovich.kvalid.core.model.Rule

/**
 * An implementation of KValidContext that aggregates (collects) validation violations.
 */
open class AggregatingValidator : KValidContext {
    /**
     * The internal list of validation violations.
     */
    private val _violations: MutableList<ValidationException> = mutableListOf()

    /**
     * A read-only view of the validation violations.
     */
    val violations: List<ValidationException>
        get() = _violations.toList()

    /**
     * Validates a value with the given [rule] and adds a violation if the validation fails.
     *
     * @param rule The rule to validate.
     */
    override fun validate(rule: Rule) {
        if (!rule.validate()) {
            _violations.add(ValidationException(rule.failMessage))
        }
    }
}
