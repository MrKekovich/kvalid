package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.dto.NamedValue

typealias NamedString = NamedValue<String>

interface StringValidationContext : ValidationContext {
    fun NamedString.notBlank(
        message: String = "$name must not be blank",
    ): NamedString =
        validate(message) { it.isNotBlank() }

    fun NamedString.ofLength(
        length: Int,
        message: String = "$name length must be $length",
    ): NamedString =
        validate(message) { it.length == length }

    fun NamedString.ofLength(
        range: IntRange,
        message: String = "$name length must be in range $range",
    ): NamedString =
        validate(message) { it.length in range }

    fun NamedString.ofLength(
        range: LongRange,
        message: String = "$name length must be in range $range",
    ): NamedString =
        validate(message) { it.length in range }

    fun NamedString.notOfLength(
        length: Int,
        message: String = "$name length must not be $length",
    ): NamedString =
        validate(message) { it.length != length }

    fun NamedString.notOfLength(
        range: IntRange,
        message: String = "$name length must not be in range $range",
    ): NamedString =
        validate(message) { it.length !in range }

    fun NamedString.notOfLength(
        range: LongRange,
        message: String = "$name length must not be in range $range",
    ): NamedString =
        validate(message) { it.length !in range }

    fun NamedString.minLength(
        min: Int,
        message: String = "$name length must be at least $min",
    ): NamedString =
        validate(message) { it.length >= min }

    fun NamedString.maxLength(
        max: Int,
        message: String = "$name length must be at most $max",
    ): NamedString =
        validate(message) { it.length <= max }

    fun NamedString.matches(
        pattern: Regex,
        message: String = "$name must match $pattern",
    ): NamedString =
        validate(message) { pattern.matches(it) }

    fun NamedString.matches(
        pattern: String,
        message: String = "$name must match $pattern",
    ): NamedString =
        matches(Regex(pattern), message)

    fun NamedString.notMatches(
        pattern: Regex,
        message: String = "$name must not match $pattern",
    ): NamedString =
        validate(message) { !pattern.matches(it) }

    fun NamedString.notMatches(
        pattern: String,
        message: String = "$name must not match $pattern",
    ): NamedString =
        notMatches(Regex(pattern), message)
}
