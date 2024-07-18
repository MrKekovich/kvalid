package io.github.mrkekovich.kvalid.core.validator

import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.context.ValidationPredicate
import io.github.mrkekovich.kvalid.core.exception.ValidationException
import io.github.mrkekovich.kvalid.core.model.Rule

open class AggregatingValidator : KValidContext {
    private val _violations: MutableList<ValidationException> = mutableListOf()

    val violations: List<ValidationException>
        get() = _violations

    override fun <T> T.validate(message: String, predicate: ValidationPredicate<T>): T {
        if (!predicate(this)) _violations.add(
            ValidationException(message)
        )

        return this
    }

    override fun rule(message: String, predicate: () -> Boolean): Rule = Rule(message) { predicate() }.also {
        if (!predicate()) _violations.add(
            ValidationException(message)
        )
    }

    override fun violation(message: String) {
        _violations.add(ValidationException(message))
    }
}
