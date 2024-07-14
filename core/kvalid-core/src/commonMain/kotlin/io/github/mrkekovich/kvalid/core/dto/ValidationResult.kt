package io.github.mrkekovich.kvalid.core.dto

import io.github.mrkekovich.kvalid.core.exception.ValidationException

sealed class ValidationResult {
    data object Valid : ValidationResult()
    data class Invalid(val errors: List<ValidationException>) : ValidationResult()

    companion object {
        fun valid(): ValidationResult = Valid

        fun invalid(errors: List<ValidationException>): ValidationResult = Invalid(errors)
        fun invalid(vararg errors: ValidationException): ValidationResult = invalid(errors.toList())

        fun invalid(errors: List<String>): ValidationResult = invalid(errors.map { ValidationException(it) })
        fun invalid(vararg errors: String): ValidationResult = invalid(errors.toList())
    }
}

val ValidationResult.isValid
    get() = this is ValidationResult.Valid

val ValidationResult.isInvalid
    get() = this is ValidationResult.Invalid

fun ValidationResult.errorsOrNull(): List<ValidationException>? = when (this) {
    is ValidationResult.Invalid -> errors
    is ValidationResult.Valid -> null
}

inline fun ValidationResult.onInvalid(block: (List<ValidationException>) -> Unit): ValidationResult = apply {
    if (this is ValidationResult.Invalid) block(errors)
}

inline fun ValidationResult.onValid(block: () -> Unit): ValidationResult = apply {
    if (this is ValidationResult.Valid) block()
}
