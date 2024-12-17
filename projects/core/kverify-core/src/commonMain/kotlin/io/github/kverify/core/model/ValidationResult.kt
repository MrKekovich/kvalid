package io.github.kverify.core.model

import io.github.kverify.core.exception.ValidationException
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

/**
 * A class representing the result of a validation operation, containing a list of validation violations if any.
 *
 * @property violations The list of validation violations.
 */
@JvmInline
value class ValidationResult(
    val violations: List<ValidationException>,
) {
    companion object {
        /**
         * Combines a list of validation results into a single validation result.
         *
         * @param results The list of validation results to combine.
         * @return A single [ValidationResult] containing the combined violations.
         */
        @JvmStatic
        fun combine(results: List<ValidationResult>): ValidationResult =
            results.fold(ValidationResult(emptyList())) { acc, result ->
                acc + result
            }
    }

    /**
     * Creates an empty validation result.
     */
    constructor() : this(emptyList())

    /**
     * Creates a validation result with the given list of validation violations.
     *
     * @param violations The list of validation violations.
     */
    constructor(vararg violations: ValidationException) : this(violations.toList())

    /**
     * Indicates whether the validation result is valid (i.e., no violations).
     */
    val isValid: Boolean get() = violations.isEmpty()

    /**
     * Indicates whether the validation result is invalid (i.e., there are violations).
     */
    val isInvalid: Boolean get() = violations.isNotEmpty()

    /**
     * Adds a single validation violation to the current validation result.
     *
     * @param violation The validation violation to add.
     * @return A new [ValidationResult] containing the updated violation list.
     */
    operator fun plus(violation: ValidationException): ValidationResult =
        ValidationResult(
            violations + violation,
        )

    /**
     * Combines two validation results by concatenating their violation lists.
     *
     * @param other The other validation result.
     * @return A new [ValidationResult] containing the combined violation lists.
     */
    operator fun plus(other: ValidationResult): ValidationResult =
        ValidationResult(
            violations + other.violations,
        )

    /**
     * Adds a list of validation violations to the current validation result.
     *
     * @param violations The list of validation violations to add.
     * @return A new [ValidationResult] containing the updated violation list.
     */
    operator fun plus(violations: List<ValidationException>): ValidationResult =
        ValidationResult(
            this.violations + violations,
        )

    /**
     * Returns the list of validation violations if there are any, otherwise returns null.
     *
     * @return The list of validation violations or null if there are no violations.
     */
    fun violationsOrNull(): List<ValidationException>? = violations.takeIf { it.isNotEmpty() }

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
     * Throws a [ValidationException] if the validation result is invalid.
     *
     * The exception message is constructed by transforming each validation violation into a string
     * and joining them with the specified formatting parameters.
     *
     * @param separator The string used to separate the transformed violation messages. Defaults to a newline (`"\n"`).
     * @param prefix The string prefixed to the resulting exception message. Defaults to `"Validation failed: "`.
     * @param postfix The string appended to the resulting exception message. Defaults to `"."`.
     * @param cause An optional [Throwable] that caused this exception, enabling exception chaining.
     * @param transform A function to transform each [ValidationException] into a string representation.
     *                  Defaults to prepending each violation message with a bullet point (`"- message"`).
     * @throws ValidationException if the validation result is invalid.
     */
    fun throwOnInvalid(
        separator: String = "\n",
        prefix: String = "Validation failed: ",
        postfix: String = ".",
        cause: Throwable? = null,
        transform: (ValidationException) -> String = { "- ${it.message}" },
    ) {
        if (isInvalid) {
            throw ValidationException(
                violations.joinToString(
                    separator = separator,
                    prefix = prefix,
                    postfix = postfix,
                    transform = transform,
                ),
                cause,
            )
        }
    }
}
