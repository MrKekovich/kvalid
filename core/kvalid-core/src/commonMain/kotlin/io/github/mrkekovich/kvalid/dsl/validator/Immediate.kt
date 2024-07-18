package io.github.mrkekovich.kvalid.dsl.validator

import io.github.mrkekovich.kvalid.core.exception.ValidationException
import io.github.mrkekovich.kvalid.core.model.ValidationResult
import io.github.mrkekovich.kvalid.core.validator.ThrowingValidator

/**
 * Executes validation rules and throws an exception on the first failure.
 *
 * This function stops execution on the first validation failure and throws a [ValidationException].
 *
 * @param block The block of validation rules to execute.
 * @throws ValidationException if validation fails.
 */
inline fun validateOrThrow(block: ThrowingValidator.() -> Unit) {
    ThrowingValidator(block)
}

/**
 * Executes validation rules and stops on the first failure, returning the result.
 *
 * This function stops execution on the first validation failure and returns a [ValidationResult]
 * indicating whether the validation was successful or not.
 *
 * @param block The block of validation rules to execute.
 * @return A [ValidationResult] indicating the outcome of the validation.
 */
inline fun validateWithFailFast(block: ThrowingValidator.() -> Unit): ValidationResult = try {
    validateOrThrow(block)
    ValidationResult.valid()
} catch (violation: ValidationException) {
    ValidationResult.invalid(violation)
}
