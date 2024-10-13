package org.kvalid.core.validator

import org.kvalid.core.context.KValidContext
import org.kvalid.core.context.Predicate
import org.kvalid.core.exception.ValidationException

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
     * Adds a [ValidationException] to [violations] with the specified [message] if the [predicate] evaluates to `false`.
     *
     * @param message The failure message.
     * @param predicate The predicate to validate the value.
     */
    override fun validate(message: String, predicate: Predicate) {
        if (!predicate()) {
            _violations.add(ValidationException(message))
        }
    }
}
