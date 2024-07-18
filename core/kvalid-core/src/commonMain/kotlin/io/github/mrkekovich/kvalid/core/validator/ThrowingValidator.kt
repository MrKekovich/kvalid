package io.github.mrkekovich.kvalid.core.validator

import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.exception.ValidationException
import io.github.mrkekovich.kvalid.core.model.Rule

/**
 * An implementation of KValidContext that immediately throws exceptions for validation failures.
 */
object ThrowingValidator : KValidContext {
    /**
     * Executes the given validation block.
     *
     * @param block The validation block to execute.
     */
    inline operator fun invoke(block: ThrowingValidator.() -> Unit): Unit = run(block)

    /**
     * Uses [Rule.validate] and throws a [ValidationException] if it returns `false`.
     *
     * @throws ValidationException if the validation fails.
     */
    override fun validate(rule: Rule) {
        if (!rule.validate()) throw ValidationException(rule.failMessage)
    }
}
