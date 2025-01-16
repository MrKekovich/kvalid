package io.github.kverify.core.validator

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.violation.Violation

/**
 * A [ValidationContext] implementation that collects validation failure messages.
 *
 * This validator aggregates all [Violation]s encountered during validation
 * without interrupting the validation process. It allows all validations
 * to run and records any violations encountered during execution.
 */
open class AggregatingValidator : ValidationContext {
    /**
     * A mutable list of validation failures collected during the validation process.
     */
    val violations: MutableList<Violation> = mutableListOf()

    /**
     * Adds the given [violation] to the list of [violations].
     *
     * This method is called whenever a validation failure occurs, allowing
     * all violations to be collected for later inspection.
     *
     * @param violation The violation to record.
     */
    override fun onFailure(violation: Violation) {
        violations.add(violation)
    }
}
