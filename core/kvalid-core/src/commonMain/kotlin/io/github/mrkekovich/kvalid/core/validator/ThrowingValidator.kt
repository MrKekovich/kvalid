package io.github.mrkekovich.kvalid.core.validator

import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.context.ValidationPredicate
import io.github.mrkekovich.kvalid.core.exception.ValidationException
import io.github.mrkekovich.kvalid.core.model.Rule

object ThrowingValidator : KValidContext {
    inline operator fun invoke(block: ThrowingValidator.() -> Unit): Unit = run(block)

    override fun rule(message: String, predicate: () -> Boolean): Rule = Rule(message, predicate).also {
        if (!it.validate()) throw ValidationException(message)
    }

    override fun violation(message: String): Nothing = throw ValidationException(message)

    override fun <T> T.validate(message: String, predicate: ValidationPredicate<T>): T {
        if (!predicate(this)) throw ValidationException(message)
        return this
    }
}
