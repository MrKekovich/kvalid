package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.model.NamedValue

typealias NamedString = NamedValue<String>

/**
 * Named string validation context.
 */
interface NamedStringValidationContext : ValidationContext {
    /**
     * Validates that the string is not blank.
     *
     * ```
     * name.withName("name").notBlank()
     * ```
     *
     * @param message the failure message if validation fails
     */
    fun NamedString.notBlank(
        message: String = "$name must not be blank",
    ): NamedString {
        value.validate(message) { it.isNotBlank() }
        return this
    }

    /**
     * Validates that the string has the specified length.
     *
     * ```
     * name.withName("name").ofLength(5)
     * ```
     *
     * @param length the required length of the string
     * @param message the failure message if validation fails
     */
    fun NamedString.ofLength(
        length: Int,
        message: String = "$name length must be $length",
    ): NamedString {
        value.validate(message) { it.length == length }
        return this
    }

    /**
     * Validates that the string length is within the specified range.
     *
     * ```
     * name.withName("name").ofLength(5..10)
     * ```
     *
     * @param range the allowed length range of the string
     * @param message the failure message if validation fails
     */
    fun NamedString.ofLength(
        range: IntRange,
        message: String = "$name length must be in range $range",
    ): NamedString {
        value.validate(message) { it.length in range }
        return this
    }

    /**
     * Validates that the string does not have the specified length.
     *
     * ```
     * name.withName("name").notOfLength(5)
     * ```
     *
     * @param length the prohibited length of the string
     * @param message the failure message if validation fails
     */
    fun NamedString.notOfLength(
        length: Int,
        message: String = "$name length must not be $length",
    ): NamedString {
        value.validate(message) { it.length != length }
        return this
    }

    /**
     * Validates that the string length is not within the specified range.
     *
     * ```
     * name.withName("name").notOfLength(5..10)
     * ```
     *
     * @param range the prohibited length range of the string
     * @param message the failure message if validation fails
     */
    fun NamedString.notOfLength(
        range: IntRange,
        message: String = "$name length must not be in range $range",
    ): NamedString {
        value.validate(message) { it.length !in range }
        return this
    }

    /**
     * Validates that the string has at least the specified minimum length.
     *
     * ```
     * name.withName("name").minLength(5)
     * ```
     *
     * @param min the minimum length of the string
     * @param message the failure message if validation fails
     */
    fun NamedString.minLength(
        min: Int,
        message: String = "$name length must be at least $min",
    ): NamedString {
        value.validate(message) { it.length >= min }
        return this
    }

    /**
     * Validates that the string has at most the specified maximum length.
     *
     * ```
     * name.withName("name").maxLength(10)
     * ```
     *
     * @param max the maximum length of the string
     * @param message the failure message if validation fails
     */
    fun NamedString.maxLength(
        max: Int,
        message: String = "$name length must be at most $max",
    ): NamedString {
        value.validate(message) { it.length <= max }
        return this
    }

    /**
     * Validates that the string matches the specified regular expression pattern.
     *
     * ```
     * name.withName("name").matches(Regex("^[a-zA-Z]+\$"))
     * ```
     *
     * @param pattern the regular expression pattern to match
     * @param message the failure message if validation fails
     */
    fun NamedString.matches(
        pattern: Regex,
        message: String = "$name must match $pattern",
    ): NamedString {
        value.validate(message) { pattern.matches(it) }
        return this
    }

    /**
     * Validates that the string matches the specified regular expression pattern.
     *
     * ```
     * name.withName("name").matches("^[a-zA-Z]+\$")
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
     * name.withName("name").notMatches(Regex("^[0-9]+\$"))
     * ```
     *
     * @param pattern the regular expression pattern to not match
     * @param message the failure message if validation fails
     */
    fun NamedString.notMatches(
        pattern: Regex,
        message: String = "$name must not match $pattern",
    ): NamedString {
        value.validate(message) { !pattern.matches(it) }
        return this
    }

    /**
     * Validates that the string does not match the specified regular expression pattern.
     *
     * ```
     * name.withName("name").notMatches("^[0-9]+\$")
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
