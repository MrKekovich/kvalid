package io.github.kverify.dsl.validator

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.Rule
import io.github.kverify.core.validator.ThrowingValidator

/**
 * Executes the validation block using a [ThrowingValidator]. Throws a [ValidationException]
 * immediately upon the first validation failure.
 *
 * @param block The block of validation logic to execute.
 * @return [Unit] if the validation succeeds.
 * @throws ValidationException if the validation fails.
 */
inline fun validateOrThrow(block: ThrowingValidator.() -> Unit): Unit = ThrowingValidator(block)

/**
 * Shortcut for [validateOrThrow], allowing validation of the current object against the provided rules.
 * Throws a [ValidationException] immediately upon the first validation failure.
 *
 * @param rules The validation rules to apply to the current object.
 * @throws ValidationException if validation fails on the first rule.
 * @see validateOrThrow
 */
fun <T> T.validateOrThrow(vararg rules: Rule<T>): Unit =
    validateOrThrow {
        validate(*rules)
    }

/**
 * Validates using `fail fast` functionality with a [ThrowingValidator].
 * Stops validation upon the first failure and returns the [ValidationException].
 * Returns `null` if the validation succeeds.
 *
 * @param block The block of validation conditions to execute.
 * @return [ValidationException] if the validation fails, or `null` if the validation succeeds.
 */
inline fun validateFirst(block: ThrowingValidator.() -> Unit): ValidationException? =
    try {
        validateOrThrow(block)
        null
    } catch (violation: ValidationException) {
        violation
    }

/**
 * Shortcut for [validateFirst] that validates the current object against the provided rules using `fail fast` functionality.
 * Stops validation upon the first failure and returns the [ValidationException].
 * Returns `null` if the validation succeeds.
 *
 * @param rules The validation rules to apply to the current object.
 * @return [ValidationException] if the validation fails, or `null` if the validation succeeds.
 * @see validateFirst
 */
fun <T> T.validateFirst(vararg rules: Rule<T>): ValidationException? =
    validateFirst {
        validate(*rules)
    }
