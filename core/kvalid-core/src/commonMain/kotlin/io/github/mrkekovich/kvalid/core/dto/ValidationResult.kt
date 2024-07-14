package io.github.mrkekovich.kvalid.core.dto

import io.github.mrkekovich.kvalid.core.exception.ValidationException

/**
 * Represents the result of a validation process.
 */
sealed class ValidationResult {

    /**
     * Represents a valid result with no validation errors.
     */
    data object Valid : ValidationResult()

    /**
     * Represents an invalid result with a list of validation errors.
     *
     * @property errors the list of validation exceptions
     */
    data class Invalid(val errors: List<ValidationException>) : ValidationResult()

    companion object {
        /**
         * Returns a valid validation result.
         *
         * @return a valid validation result
         */
        fun valid(): ValidationResult = Valid

        /**
         * Returns an invalid validation result with the given list of validation exceptions.
         *
         * @param errors the list of validation exceptions
         * @return an invalid validation result
         */
        fun invalid(errors: List<ValidationException>): ValidationResult = Invalid(errors)

        /**
         * Returns an invalid validation result with the given validation exceptions.
         *
         * @param errors the validation exceptions
         * @return an invalid validation result
         */
        fun invalid(vararg errors: ValidationException): ValidationResult = invalid(errors.toList())

        /**
         * Returns an invalid validation result with the given list of error messages.
         *
         * @param errors the list of error messages
         * @return an invalid validation result
         */
        fun invalid(errors: List<String>): ValidationResult = invalid(errors.map { ValidationException(it) })

        /**
         * Returns an invalid validation result with the given error messages.
         *
         * @param errors the error messages
         * @return an invalid validation result
         */
        fun invalid(vararg errors: String): ValidationResult = invalid(errors.toList())
    }
}

/**
 * Extension property to check if the validation result is valid.
 *
 * @return true if the validation result is valid, false otherwise
 */
val ValidationResult.isValid
    get() = this is ValidationResult.Valid

/**
 * Extension property to check if the validation result is invalid.
 *
 * @return true if the validation result is invalid, false otherwise
 */
val ValidationResult.isInvalid
    get() = this is ValidationResult.Invalid

/**
 * Returns the list of validation errors if the validation result is invalid, or null if it is valid.
 *
 * @return the list of validation errors or null
 */
fun ValidationResult.errorsOrNull(): List<ValidationException>? = when (this) {
    is ValidationResult.Invalid -> errors
    is ValidationResult.Valid -> null
}

/**
 * Returns the list of validation errors if the validation result is invalid, or an empty list if it is valid.
 *
 * @return the list of validation errors
 */
fun ValidationResult.errorsOrEmpty(): List<ValidationException> = errorsOrNull() ?: emptyList()

/**
 * Executes the given [block] if the validation result is invalid.
 *
 * @param block the block to execute with the list of validation errors
 * @return the original `ValidationResult` unchanged
 */
inline fun ValidationResult.onInvalid(block: (List<ValidationException>) -> Unit): ValidationResult = apply {
    if (this is ValidationResult.Invalid) block(errors)
}

/**
 * Executes the given [block] if the validation result is valid.
 *
 * @param block the block to execute
 * @return the original `ValidationResult` unchanged
 */
inline fun ValidationResult.onValid(block: () -> Unit): ValidationResult = apply {
    if (this is ValidationResult.Valid) block()
}

/**
 * Applies the given [onValid] function if the validation result is valid, or the [onInvalid] function if it is invalid.
 *
 * @param onValid the function to apply if the validation result is valid
 * @param onInvalid the function to apply if the validation result is invalid
 * @return the result of applying the function
 */
inline fun <T> ValidationResult.fold(
    onValid: () -> T,
    onInvalid: (List<ValidationException>) -> T
): T = when (this) {
    is ValidationResult.Valid -> onValid()
    is ValidationResult.Invalid -> onInvalid(errors)
}
