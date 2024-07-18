package io.github.mrkekovich.kvalid.core.validator

import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.exception.ValidationException
import io.github.mrkekovich.kvalid.core.model.Rule

open class AggregatingValidator : KValidContext {
    private val _violations: MutableList<ValidationException> = mutableListOf()

    val violations: List<ValidationException>
        get() = _violations

    override fun validate(rule: Rule) {
        if (!rule.validate()) {
            _violations.add(ValidationException(rule.failMessage))
        }
    }
}
