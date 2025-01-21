package io.github.kverify.dsl.validator

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.Rule
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.validator.ThrowingValidator

/**
 * Executes the provided [block] of validation logic using a [ThrowingValidator].
 *
 * If any validation fails, a [ValidationException] is immediately thrown.
 * This function does not return any value and does not aggregate validation violations.
 *
 * @param block The validation logic to execute
 * @throws ValidationException if any validation fails
 */
inline fun validateOrThrow(block: ThrowingValidator.() -> Unit) {
    ThrowingValidator().apply(block)
}

/**
 * Validates the receiver against the provided [rules], throwing an exception on the first failure.
 *
 * Uses [validateOrThrow] to perform the validation. If any rule fails,
 * a [ValidationException] is thrown immediately.
 *
 * @param T The type of the value being validated
 * @param rules The rules to validate the receiver against
 * @throws ValidationException if any validation fails
 */
fun <T> T.validateOrThrow(vararg rules: Rule<T>): Unit =
    validateOrThrow {
        applyRules(*rules)
    }

/**
 * Executes the provided [block] of validation logic using a [ThrowingValidator],
 * capturing the first validation failure as a [ValidationException].
 *
 * If any validation fails, the exception is caught and returned. If no failures occur, `null` is returned.
 *
 * @param block The validation logic to execute
 * @return A [ValidationException] if a validation failure occurs, or `null` if all validations pass
 */
inline fun validateFirst(block: ThrowingValidator.() -> Unit): ValidationResult =
    try {
        validateOrThrow(block)
        ValidationResult.VALID
    } catch (violation: ValidationException) {
        ValidationResult(violation.violations)
    }

/**
 * Validates the receiver against the provided [rules], returning the first failure if any.
 *
 * Uses [validateFirst] to perform the validation. If any rule fails, the first [ValidationException]
 * is returned. If no rules fail, `null` is returned.
 *
 * @param T The type of the value being validated
 * @param rules The rules to validate the receiver against
 * @return A [ValidationException] if a validation failure occurs, or `null` if all validations pass
 */
fun <T> T.validateFirst(vararg rules: Rule<T>): ValidationResult =
    validateFirst {
        applyRules(*rules)
    }

/**
 * Executes the given [block] with a [ThrowingValidator] that throws on the first validation failure.
 *
 * If a [ValidationException] is thrown during validation, this function catches it and returns a failed [Result].
 * If no exception is thrown, it returns a successful [Result] containing the result of [block].
 *
 * @param block The block of validation logic to execute.
 * @return A [Result] containing either the result of [block] or the thrown [ValidationException].
 */
inline fun <T> runValidatingFirst(block: ThrowingValidator.() -> T): Result<T> =
    try {
        Result.success(
            ThrowingValidator().run(block),
        )
    } catch (e: ValidationException) {
        Result.failure(e)
    }
