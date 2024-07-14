package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.dto.NamedValue

typealias NamedString = NamedValue<String>

/**
 * String validation context
 */
interface StringValidationContext : ValidationContext {
    /**
     * Validates that the string is not blank.
     *
     * ```
     * name.named("name").notBlank()
     * ```
     *
     * @param message the failure message if validation fails
     */
    fun NamedString.notBlank(
        message: String = "$name must not be blank",
    ): NamedString =
        validate(message) { it.isNotBlank() }

    /**
     * Validates that the string has the specified length.
     *
     * ```
     * name.named("name").ofLength(5)
     * ```
     *
     * @param length the required length of the string
     * @param message the failure message if validation fails
     */
    fun NamedString.ofLength(
        length: Int,
        message: String = "$name length must be $length",
    ): NamedString =
        validate(message) { it.length == length }

    /**
     * Validates that the string length is within the specified range.
     *
     * ```
     * name.named("name").ofLength(5..10)
     * ```
     *
     * @param range the allowed length range of the string
     * @param message the failure message if validation fails
     */
    fun NamedString.ofLength(
        range: IntRange,
        message: String = "$name length must be in range $range",
    ): NamedString =
        validate(message) { it.length in range }

    /**
     * Validates that the string does not have the specified length.
     *
     * ```
     * name.named("name").notOfLength(5)
     * ```
     *
     * @param length the prohibited length of the string
     * @param message the failure message if validation fails
     */
    fun NamedString.notOfLength(
        length: Int,
        message: String = "$name length must not be $length",
    ): NamedString =
        validate(message) { it.length != length }

    /**
     * Validates that the string length is not within the specified range.
     *
     * ```
     * name.named("name").notOfLength(5..10)
     * ```
     *
     * @param range the prohibited length range of the string
     * @param message the failure message if validation fails
     */
    fun NamedString.notOfLength(
        range: IntRange,
        message: String = "$name length must not be in range $range",
    ): NamedString =
        validate(message) { it.length !in range }

    /**
     * Validates that the string has at least the specified minimum length.
     *
     * ```
     * name.named("name").minLength(5)
     * ```
     *
     * @param min the minimum length of the string
     * @param message the failure message if validation fails
     */
    fun NamedString.minLength(
        min: Int,
        message: String = "$name length must be at least $min",
    ): NamedString =
        validate(message) { it.length >= min }

    /**
     * Validates that the string has at most the specified maximum length.
     *
     * ```
     * name.named("name").maxLength(10)
     * ```
     *
     * @param max the maximum length of the string
     * @param message the failure message if validation fails
     */
    fun NamedString.maxLength(
        max: Int,
        message: String = "$name length must be at most $max",
    ): NamedString =
        validate(message) { it.length <= max }

    /**
     * Validates that the string matches the specified regular expression pattern.
     *
     * ```
     * name.named("name").matches(Regex("^[a-zA-Z]+\$"))
     * ```
     *
     * @param pattern the regular expression pattern to match
     * @param message the failure message if validation fails
     */
    fun NamedString.matches(
        pattern: Regex,
        message: String = "$name must match $pattern",
    ): NamedString =
        validate(message) { pattern.matches(it) }

    /**
     * Validates that the string matches the specified regular expression pattern.
     *
     * ```
     * name.named("name").matches("^[a-zA-Z]+\$")
     * ```
     *
     * @param pattern the regular expression pattern to match
     * @param message the failure message if validation fails
     */
    fun NamedString.matches(
        pattern: String,
        message: String = "$name must match $pattern",
    ): NamedString =
        matches(Regex(pattern), message)

    /**
     * Validates that the string does not match the specified regular expression pattern.
     *
     * ```
     * name.named("name").notMatches(Regex("^[0-9]+\$"))
     * ```
     *
     * @param pattern the regular expression pattern to not match
     * @param message the failure message if validation fails
     */
    fun NamedString.notMatches(
        pattern: Regex,
        message: String = "$name must not match $pattern",
    ): NamedString =
        validate(message) { !pattern.matches(it) }

    /**
     * Validates that the string does not match the specified regular expression pattern.
     *
     * ```
     * name.named("name").notMatches("^[0-9]+\$")
     * ```
     *
     * @param pattern the regular expression pattern to not match
     * @param message the failure message if validation fails
     */
    fun NamedString.notMatches(
        pattern: String,
        message: String = "$name must not match $pattern",
    ): NamedString =
        notMatches(Regex(pattern), message)
}
