package io.github.kverify.core.validator

import io.github.kverify.core.context.Predicate
import io.github.kverify.core.context.ValidationContext

/**
 * A [ValidationContext] implementation that collects validation failure messages.
 *
 * This validator aggregates all failure messages into [violationMessages] without interrupting
 * the validation process. It allows all validations to run and records any violations.
 */
open class AggregatingValidator : ValidationContext {
    /**
     * A mutable list of validation failure messages collected during the validation process.
     */
    val violationMessages: MutableList<String> = mutableListOf()

    /**
     * Adds the specified [message] to [violationMessages] if the [predicate] evaluates to `false`.
     *
     * This method performs the validation by evaluating the [predicate]. If the predicate fails,
     * the failure message is recorded.
     *
     * @param message The failure message to record if validation fails
     * @param predicate The predicate that determines whether the validation passes
     */
    override fun validate(
        message: String,
        predicate: Predicate,
    ) {
        if (!predicate()) {
            violationMessages.add(message)
        }
    }
}
