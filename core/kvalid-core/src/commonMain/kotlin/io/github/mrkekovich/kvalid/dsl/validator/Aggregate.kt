package io.github.mrkekovich.kvalid.dsl.validator

import io.github.mrkekovich.kvalid.core.model.ValidationResult
import io.github.mrkekovich.kvalid.core.validator.AggregatingValidator

/**
 * Executes validation rules within an aggregate context and returns the result.
 *
 * This function collects all validation violations and returns a [ValidationResult]
 * indicating whether the validation was successful or not.
 *
 * @param block The block of validation rules to execute.
 * @return A [ValidationResult] indicating the outcome of the validation.
 */
inline fun validateAll(block: AggregatingValidator.() -> Unit): ValidationResult {
    val violations = AggregatingValidator().apply(block).violations

    return ValidationResult(violations)
}
