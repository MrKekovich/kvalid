package io.github.kverify.core.validator

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.Rule
import io.github.kverify.core.model.ValidationResult
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

/**
 * Collects all validation violations into a single [ValidationException].
 *
 * Executes the provided [block] of validation logic using an [AggregatingValidator].
 * If any validation failures occur, a [ValidationException] is returned,
 * containing all violation messages. If no failures occur, `null` is returned.
 *
 * @param block The validation logic to execute
 * @return A [ValidationException] if there are validation failures, or `null` if all validations pass
 */
inline fun validateAll(block: AggregatingValidator.() -> Unit): ValidationResult =
    ValidationResult(
        AggregatingValidator().apply(block).violations,
    )

/**
 * Validates the receiver against the provided [rules],
 * aggregating any validation violations into a single [ValidationException].
 *
 * Uses [validateAll] to perform the validation. If any rules fail,
 * the resulting [ValidationException] contains all violation messages.
 *
 * @param T The type of the value being validated
 * @param rules The rules to validate the receiver against
 * @return A [ValidationException] if there are validation failures, or `null` if all validations pass
 */
fun <T> T.validateAll(vararg rules: Rule<T>): ValidationResult =
    validateAll lambda@{
        this@validateAll.applyRules(*rules)
    }

/**
 * Executes a block of validation logic using an [AggregatingValidator] and returns the [Result].
 *
 * The provided [block] is executed within the context of an [AggregatingValidator]. If any validation
 * violations are recorded during the execution of the [block], this function returns a [Result] containing
 * a [ValidationException] with all violation messages. If no violations occur, the result of the [block]
 * is returned as a successful [Result].
 *
 * @param T The type of the value produced by the [block].
 * @param block The validation logic to execute within the context of an [AggregatingValidator].
 * @return A [Result.success] containing the result of the [block] if all validations pass,
 * or a [Result.failure] containing a [ValidationException] if there are validation failures.
 */
inline fun <T> runValidatingAll(block: AggregatingValidator.() -> T): Result<T> =
    AggregatingValidator().run {
        val result = this.block()

        if (violations.isEmpty()) {
            Result.success(result)
        } else {
            Result.failure(
                ValidationException(violations),
            )
        }
    }
