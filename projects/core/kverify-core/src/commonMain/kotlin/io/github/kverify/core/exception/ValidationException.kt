package io.github.kverify.core.exception

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolation
import kotlin.jvm.JvmName

open class ValidationException(
    override val message: String? = null,
    val violations: List<Violation> = emptyList(),
    override val cause: Throwable? = null,
) : Throwable()

fun ValidationException(
    violations: List<Violation>,
    cause: Throwable? = null,
): ValidationException {
    val message =
        if (violations.isEmpty()) {
            "Validation failed"
        } else {
            "Validation failed: ${violations.joinToString(", ") { it.message }}"
        }

    return ValidationException(
        violations = violations,
        message = message,
        cause = cause,
    )
}

fun ValidationException(
    vararg violations: Violation,
    cause: Throwable? = null,
): ValidationException =
    ValidationException(
        violations.asList(),
        cause,
    )

fun ValidationException(
    message: String? = null,
    violationMessages: List<String> = emptyList(),
    cause: Throwable? = null,
): ValidationException =
    ValidationException(
        message,
        violationMessages.map { it.asViolation() },
        cause,
    )

@JvmName("ValidationExceptionString")
fun ValidationException(
    violationMessages: List<String> = emptyList(),
    cause: Throwable? = null,
): ValidationException =
    ValidationException(
        violationMessages.map { it.asViolation() },
        cause,
    )
