package io.github.mrkekovich.kvalid.core.validator

import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.exception.ValidationException
import io.github.mrkekovich.kvalid.core.model.Rule

object ThrowingValidator : KValidContext {
    inline operator fun invoke(block: ThrowingValidator.() -> Unit): Unit = run(block)

    /**
     * Adds a validation rule and immediately checks it, throwing an exception if it fails.
     *
     * @param rule The Rule to add.
     * @throws ValidationException if the validation fails.
     */
    override fun validate(rule: Rule) {
        if (!rule.validate()) throw ValidationException(rule.failMessage)
    }
}