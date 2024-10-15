package io.github.kverify.dsl.validator

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
