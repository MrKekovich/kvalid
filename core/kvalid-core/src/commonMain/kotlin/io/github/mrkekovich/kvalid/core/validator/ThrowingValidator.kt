package io.github.mrkekovich.kvalid.core.validator

import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.context.Predicate
import io.github.mrkekovich.kvalid.core.context.ValuePredicate
import io.github.mrkekovich.kvalid.core.exception.ValidationException
import io.github.mrkekovich.kvalid.core.model.Rule

object ThrowingValidator : KValidContext {
    inline operator fun invoke(block: ThrowingValidator.() -> Unit): Unit = run(block)

    override fun rule(message: String, predicate: Predicate): Rule = Rule(message, predicate).also {
        if (!it.validate()) throw ValidationException(message)
    }

    /**
     * Adds a validation rule and immediately checks it, throwing an exception if it fails.
     *
     * @param rule The Rule to add.
     * @throws ValidationException if the validation fails.
     */
    override fun rule(rule: Rule) {
        if (!rule.validate()) throw ValidationException(rule.failMessage)
    }

    override fun violation(message: String): Nothing = throw ValidationException(message)

    override fun <T> T.validate(message: String, predicate: ValuePredicate<T>): T {
        if (!predicate(this)) throw ValidationException(message)
        return this
    }
}
