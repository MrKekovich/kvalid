package io.github.kverify.dsl.validator

import io.github.kverify.core.model.Rule
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.validator.AggregatingValidator

/**
 * Validates all conditions defined within the given block using an AggregatingValidator.
 * Collects all violations and returns a ValidationResult.
 *
 * @param block The block of validation conditions to execute.
 * @return [ValidationResult] containing all violations found during validation.
 */
inline fun validateAll(block: AggregatingValidator.() -> Unit): ValidationResult {
    val violations = AggregatingValidator().apply(block).violations

    return ValidationResult(violations)
}

/**
 * Shortcut for [validateAll], allowing validation of the current object against the provided rules.
 * Collects all violations and returns a ValidationResult.
 *
 * @param rules The validation rules to apply to the current object.
 * @return [ValidationResult] containing all violations found during validation.
 * @see validateAll
 */
fun <T> T.validateAll(vararg rules: Rule<T>): ValidationResult =
    validateAll {
        validate(*rules)
    }
