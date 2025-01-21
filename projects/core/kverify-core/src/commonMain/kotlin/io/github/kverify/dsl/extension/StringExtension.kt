package io.github.kverify.dsl.extension

import io.github.kverify.core.violation.Violation

/**
 * Converts a [String] message into a [Violation].
 *
 * This extension function allows a simple string message to be treated as a violation, which
 * can then be processed or used in validation contexts.
 *
 * @return A [Violation] instance containing the string as its message.
 */
fun String.asViolation(): Violation = Violation(this)
