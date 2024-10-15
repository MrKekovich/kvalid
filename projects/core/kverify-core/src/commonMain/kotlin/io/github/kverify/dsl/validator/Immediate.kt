package io.github.kverify.dsl.validator

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.validator.ThrowingValidator

/**
 * Executes the validation block using a [ThrowingValidator]. Throws a [ValidationException]
 * immediately upon the first validation failure.
 *
 * @param block The block of validation logic to execute.
 * @return [Unit] if the validation succeeds.
 * @throws ValidationException if the validation fails.
 */
inline fun throwOnFailure(block: ThrowingValidator.() -> Unit): Unit = ThrowingValidator(block)

/**
 * Validates using `fail fast` functionality with a [ThrowingValidator].
 * Stops validation upon the first failure and returns a [ValidationResult].
 *
 * @param block The block of validation conditions to execute.
 * @return [ValidationResult] indicating success or failure of the validation.
 */
inline fun validateFirst(block: ThrowingValidator.() -> Unit): ValidationResult =
    try {
        throwOnFailure(block)
        ValidationResult.valid()
    } catch (violation: ValidationException) {
        ValidationResult.invalid(violation)
    }
