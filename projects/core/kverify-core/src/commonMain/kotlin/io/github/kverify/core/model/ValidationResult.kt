package io.github.kverify.core.model

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.violation.Violation
import kotlin.jvm.JvmInline

@JvmInline
value class ValidationResult(
    val violations: List<Violation>,
) {
    inline val isValid: Boolean
        get() = violations.isEmpty()

    inline val isInvalid: Boolean
        get() = violations.isNotEmpty()

    operator fun plus(other: ValidationResult): ValidationResult =
        ValidationResult(
            violations + other.violations,
        )

    override fun toString(): String =
        if (isValid) {
            "ValidationResult(valid=true)"
        } else {
            "ValidationResult(valid=false, violations=${
                violations.joinToString(
                    separator = ", ",
                    prefix = "[",
                    postfix = "]",
                    limit = 10,
                )
            })"
        }

    companion object {
        val VALID = ValidationResult(emptyList())
    }
}

inline fun ValidationResult(vararg violations: Violation): ValidationResult =
    ValidationResult(
        violations.asList(),
    )

fun ValidationResult.merge(results: List<ValidationResult>): ValidationResult =
    ValidationResult(
        violations + results.flatMap { it.violations },
    )

inline fun ValidationResult.onValid(block: () -> Unit): ValidationResult {
    if (isValid) block()
    return this
}

inline fun ValidationResult.onInvalid(block: (List<Violation>) -> Unit): ValidationResult {
    if (isInvalid) block(violations)
    return this
}

inline fun <T> ValidationResult.fold(
    onValid: () -> T,
    onInvalid: (List<Violation>) -> T,
): T =
    if (isValid) {
        onValid()
    } else {
        onInvalid(violations)
    }

@Suppress("LongParameterList")
fun ValidationResult.throwOnFailure(
    separator: CharSequence = ", ",
    prefix: CharSequence = "Validation failed: [",
    postfix: CharSequence = "]",
    limit: Int = -1,
    truncated: CharSequence = "...",
    cause: Throwable? = null,
    transform: (Violation) -> String = { it.message },
) {
    if (isValid) return

    throw ValidationException(
        message =
            violations.joinToString(
                separator = separator,
                prefix = prefix,
                postfix = postfix,
                limit = limit,
                truncated = truncated,
                transform = transform,
            ),
        violations = violations,
        cause = cause,
    )
}

inline fun ValidationResult.asExceptionOrNull(
    message: String? = null,
    cause: Throwable? = null,
): ValidationException? =
    fold(
        onValid = { null },
        onInvalid = {
            ValidationException(
                message = message,
                violations = it,
                cause = cause,
            )
        },
    )

inline fun ValidationResult.asExceptionOrNull(cause: Throwable? = null): ValidationException? =
    fold(
        onValid = { null },
        onInvalid = {
            ValidationException(
                violations = it,
                cause = cause,
            )
        },
    )
