package io.github.mrkekovich.kvalid.dsl.validator

import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.exception.ValidationException
import io.github.mrkekovich.kvalid.core.model.ValidationResult
import io.github.mrkekovich.kvalid.core.validator.LazyValidator

/**
 * Executes validation rules sequentially and returns a sequence of validation exceptions.
 *
 * This function evaluates validation rules lazily, returning a sequence of [ValidationException]s
 * encountered during validation.
 *
 * @param block The block of validation rules to execute.
 * @return A sequence of [ValidationException]s.
 */
inline fun validateLazily(block: LazyValidator.() -> Unit): Sequence<ValidationException> =
    LazyValidator().apply(block).result

/**
 * Converts a sequence of [ValidationException] instances to a [ValidationResult].
 *
 * If the sequence is empty, the result is valid. Otherwise, the result is invalid and contains the list of violations.
 *
 * @return A [ValidationResult] representing the outcome of the validation.
 */
fun Sequence<ValidationException>.toValidationResult(): ValidationResult {
    val violations = this.toList()

    return ValidationResult(violations)
}

