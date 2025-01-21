package io.github.kverify.core.model

import io.github.kverify.core.violation.Violation
import kotlin.jvm.JvmInline

/**
 * Represents the result of a validation process.
 *
 * This class encapsulates a list of violations and provides utility
 * functions to check validity, combine validation results, and produce string representations.
 *
 * @property violations A list of violations describing validation failures.
 */
@JvmInline
value class ValidationResult(
    val violations: List<Violation>,
) {
    /**
     * Indicates whether the validation result is valid (i.e., no violations are present).
     */
    inline val isValid: Boolean
        get() = violations.isEmpty()

    /**
     * Indicates whether the validation result is invalid (i.e., at least one violation is present).
     */
    inline val isInvalid: Boolean
        get() = violations.isNotEmpty()

    /**
     * Combines this validation result with another validation result.
     *
     * The violation messages from both results are merged into a new instance.
     *
     * @param other The other [ValidationResult] to combine with.
     * @return A new [ValidationResult] containing violation messages from both results.
     */
    operator fun plus(other: ValidationResult): ValidationResult =
        ValidationResult(
            violations + other.violations,
        )

    /**
     * Combines this validation result with a list of other validation results.
     *
     * All violation messages are merged into a new instance.
     *
     * @param results A list of [ValidationResult] instances to combine with.
     * @return A new [ValidationResult] containing violation messages from all results.
     */
    operator fun plus(results: List<ValidationResult>): ValidationResult =
        ValidationResult(
            violations + results.flatMap { it.violations },
        )

    /**
     * Returns a string representation of the validation result.
     *
     * @return A string indicating whether the validation is valid or invalid, along with violations if any.
     */
    override fun toString(): String =
        if (isValid) {
            "ValidationResult(valid=true)"
        } else {
            "ValidationResult(valid=false, violations=$violations)"
        }

    companion object {
        /**
         * An instance representing a valid validation result.
         */
        val VALID = ValidationResult(emptyList())
    }
}

/**
 * Creates a [ValidationResult] from one or more violations.
 *
 * @param violations The violations to include in the validation result.
 * @return A new [ValidationResult] containing the provided violations.
 */
inline fun ValidationResult(vararg violations: Violation): ValidationResult =
    ValidationResult(
        violations.asList(),
    )
