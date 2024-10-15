package io.github.kverify.core.model

import io.github.kverify.core.exception.ValidationException
import kotlin.jvm.JvmInline

/**
 * A class representing the result of a validation operation, containing a list of validation violations if any.
 *
 * @property violations The list of validation violations.
 */
@JvmInline
value class ValidationResult(
    val violations: List<ValidationException>,
) {
    /**
     * Indicates whether the validation result is valid (i.e., no violations).
     */
    val isValid: Boolean get() = violations.isEmpty()

    /**
     * Indicates whether the validation result is invalid (i.e., there are violations).
     */
    val isInvalid: Boolean get() = violations.isNotEmpty()

    /**
     * Represents the type of validation result.
     */
    val type: Type get() = if (isValid) Type.VALID else Type.INVALID

    /**
     * Returns the list of validation violations if there are any, otherwise returns null.
     *
     * @return The list of validation violations or null if there are no violations.
     */
    fun violationsOrNull(): List<ValidationException>? = violations.takeIf { it.isNotEmpty() }

    companion object {
        /**
         * Creates a [ValidationResult] representing a successful validation with no violations.
         *
         * @return A [ValidationResult] with no violations.
         */
        fun valid(): ValidationResult = ValidationResult(emptyList())

        /**
         * Creates a [ValidationResult] representing a failed validation with the specified violations.
         *
         * @param violations The list of validation violations.
         * @return A [ValidationResult] with the specified violations.
         */
        fun invalid(violations: List<ValidationException>): ValidationResult = ValidationResult(violations)

        /**
         * Creates a [ValidationResult] representing a failed validation with the specified violations.
         *
         * @param violations The validation violations.
         * @return A [ValidationResult] with the specified violations.
         */
        fun invalid(vararg violations: ValidationException): ValidationResult = ValidationResult(violations.toList())

        /**
         * Creates a [ValidationResult] representing a failed validation with the specified error messages.
         *
         * @param violations The error messages.
         * @return A [ValidationResult] with the specified error messages converted to [ValidationException]s.
         */
        fun invalid(vararg violations: String): ValidationResult =
            ValidationResult(
                violations.map { ValidationException(it) },
            )

        /**
         * Combines multiple [ValidationResult]s into a single [ValidationResult].
         *
         * @param results The list of [ValidationResult]s to combine.
         * @return A [ValidationResult] containing all violations from the provided results.
         */
        fun combine(results: List<ValidationResult>): ValidationResult {
            val combinedErrors = results.flatMap { it.violations }
            return ValidationResult(combinedErrors)
        }
    }

    /**
     * Executes the given block if the validation result is invalid.
     *
     * @param block The block to execute if the validation result is invalid.
     * @return The current [ValidationResult].
     */
    inline fun onInvalid(block: (List<ValidationException>) -> Unit): ValidationResult {
        if (isInvalid) block(violations)
        return this
    }

    /**
     * Executes the given block if the validation result is valid.
     *
     * @param block The block to execute if the validation result is valid.
     * @return The current [ValidationResult].
     */
    inline fun onValid(block: () -> Unit): ValidationResult {
        if (isValid) block()
        return this
    }

    /**
     * Applies one of the given functions depending on whether the validation result is valid or invalid.
     *
     * @param T The return type of the functions.
     * @param onValid The function to apply if the validation result is valid.
     * @param onInvalid The function to apply if the validation result is invalid.
     * @return The result of applying either the valid or invalid function.
     */
    inline fun <T> fold(
        onValid: () -> T,
        onInvalid: (List<ValidationException>) -> T,
    ): T = if (isValid) onValid() else onInvalid(violations)

    /**
     * Represents the type of validation result.
     */
    enum class Type {
        VALID,
        INVALID,
    }
}
