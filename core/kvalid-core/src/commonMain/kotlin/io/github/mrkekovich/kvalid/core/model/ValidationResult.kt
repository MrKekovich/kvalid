package io.github.mrkekovich.kvalid.core.model

import io.github.mrkekovich.kvalid.core.exception.ValidationException
import kotlin.jvm.JvmInline

@JvmInline
value class ValidationResult(
    val violations: List<ValidationException>
) {
    val isValid: Boolean get() = violations.isEmpty()

    val isInvalid: Boolean get() = violations.isNotEmpty()

    val type: Type get() = if (isValid) Type.VALID else Type.INVALID

    fun errorsOrNull(): List<ValidationException>? = violations.takeIf { it.isNotEmpty() }

    companion object {
        fun valid(): ValidationResult = ValidationResult(emptyList())

        fun invalid(errors: List<ValidationException>): ValidationResult = ValidationResult(errors)

        fun invalid(vararg errors: ValidationException): ValidationResult = ValidationResult(errors.toList())

        fun invalid(vararg errors: String): ValidationResult = ValidationResult(errors.map { ValidationException(it) })

        fun combine(results: List<ValidationResult>): ValidationResult {
            val combinedErrors = results.flatMap { it.violations }
            return ValidationResult(combinedErrors)
        }
    }

    inline fun onInvalid(block: (List<ValidationException>) -> Unit): ValidationResult {
        if (isInvalid) block(violations)
        return this
    }

    inline fun onValid(block: () -> Unit): ValidationResult {
        if (isValid) block()
        return this
    }

    inline fun <T> fold(
        onValid: () -> T,
        onInvalid: (List<ValidationException>) -> T
    ): T = if (isValid) onValid() else onInvalid(violations)

    enum class Type {
        VALID, INVALID
    }
}
