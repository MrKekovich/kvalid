package io.github.mrkekovich.kvalid.core.dto

/**
 * Represents a validation rule for a value.
 *
 * @param T the type of the value
 * @property value the value to be validated
 * @property message the error message if the validation fails
 * @property predicate the predicate to validate the value
 */
data class ValidationRule<T>(
    val value: T,
    val message: String,
    val predicate: (T) -> Boolean,
) {
    /**
     * Validates the value against the predicate.
     *
     * @return true if the value is valid, false otherwise
     */
    fun validate(): Boolean = predicate(value)
}
