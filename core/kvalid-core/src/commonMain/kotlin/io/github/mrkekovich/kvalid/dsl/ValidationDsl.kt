package io.github.mrkekovich.kvalid.dsl

import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.model.ValidationResult
import io.github.mrkekovich.kvalid.core.exception.ValidationException
import io.github.mrkekovich.kvalid.core.validator.AggregatingValidator
import io.github.mrkekovich.kvalid.core.validator.LazyValidator

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

    return when {
        violations.isEmpty() -> ValidationResult.valid()
        else -> ValidationResult.invalid(violations)
    }
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
inline fun validateWithFailFast(block: KValidContext.() -> Unit): ValidationResult = try {
    KValidContext(block)
    ValidationResult.valid()
} catch (e: ValidationException) {
    ValidationResult.invalid(e)
}

/**
 * Executes validation rules and throws an exception on the first failure.
 *
 * This function stops execution on the first validation failure and throws a [ValidationException].
 *
 * @param block The block of validation rules to execute.
 * @throws ValidationException if validation fails.
 */
inline fun validateOrThrow(block: KValidContext.() -> Unit) {
    KValidContext(block)
}

/**
 * Executes validation rules sequentially and returns a sequence of validation exceptions.
 *
 * This function evaluates validation rules lazily, returning a sequence of [ValidationException]s
 * encountered during validation.
 *
 * @param block The block of validation rules to execute.
 * @return A sequence of [ValidationException]s.
 */
inline fun validateLazily(block: KValidContext.() -> Unit): Sequence<ValidationException> =
    LazyValidator().apply(block).result
