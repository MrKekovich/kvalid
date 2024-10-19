package io.github.kverify.core.validator

import io.github.kverify.core.context.Predicate
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.exception.ValidationException

/**
 * An implementation of ValidationContext that aggregates (collects) validation violations.
 */
open class AggregatingValidator : ValidationContext {
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
     * Adds a [ValidationException] to [violations]
     * with the specified [message] if the [predicate] evaluates to `false`.
     *
     * @param message The failure message.
     * @param predicate The predicate to validate the value.
     */
    override fun validate(
        message: String,
        predicate: Predicate,
    ) {
        if (!predicate()) {
            _violations.add(ValidationException(message))
        }
    }
}
