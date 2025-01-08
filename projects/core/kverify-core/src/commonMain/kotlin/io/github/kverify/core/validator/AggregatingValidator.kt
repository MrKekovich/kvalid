package io.github.kverify.core.validator

import io.github.kverify.core.context.ValidationContext

/**
 * A [ValidationContext] implementation that collects validation failure messages.
 *
 * This validator aggregates all failure messages into [violationMessages]
 * without interrupting the validation process. It allows all validations
 * to run and records any violations encountered during execution.
 */
open class AggregatingValidator : ValidationContext {
    /**
     * A mutable list of validation failure messages collected during the validation process.
     */
    val violationMessages: MutableList<String> = mutableListOf()

    /**
     * Adds the given [message] to the list of [violationMessages].
     *
     * This method is called whenever a validation failure occurs, allowing
     * all failure messages to be collected for later inspection.
     *
     * @param message The failure message to record.
     */
    override fun onFailure(message: String) {
        violationMessages.add(message)
    }
}
