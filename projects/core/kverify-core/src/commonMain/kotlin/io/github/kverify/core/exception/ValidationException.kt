package io.github.kverify.core.exception

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolation
import kotlin.jvm.JvmName

open class ValidationException(
    message: String? = null,
    val violations: List<Violation> = emptyList(),
    cause: Throwable? = null,
) : Throwable(
        message = message,
        cause = cause,
    )

fun ValidationException(
    violations: List<Violation>,
    cause: Throwable? = null,
): ValidationException {
    val message =
        if (violations.isEmpty()) {
            "Validation failed"
        } else {
            "Validation failed: \n${
                violations.joinToString("\n") {
                    "\t- ${it.message}"
                }
            }"
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

@JvmName("ValidationExceptionFromMessages")
fun ValidationException(
    violationMessages: List<String> = emptyList(),
    cause: Throwable? = null,
): ValidationException =
    ValidationException(
        violationMessages.map { it.asViolation() },
        cause,
    )
