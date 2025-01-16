package io.github.kverify.rule.violation

import io.github.kverify.core.violation.Violation

sealed interface StringViolation : Violation {
    data class OfLength(
        override val message: String,
        val length: Int,
    ) : StringViolation

    data class NotOfLength(
        override val message: String,
        val length: Int,
    ) : StringViolation

    data class MinLength(
        override val message: String,
        val min: Int,
    ) : StringViolation

    data class MaxLength(
        override val message: String,
        val max: Int,
    ) : StringViolation

    data class LengthBetween(
        override val message: String,
        val range: IntRange,
    ) : StringViolation

    data class LengthNotBetween(
        override val message: String,
        val range: IntRange,
    ) : StringViolation

    data class Contains(
        override val message: String,
        val string: String,
    ) : StringViolation

    data class NotContains(
        override val message: String,
        val string: String,
    ) : StringViolation

    data class ContainsOnly(
        override val message: String,
        val chars: Set<Char>,
    ) : StringViolation

    data class ContainsAll(
        override val message: String,
        val chars: Set<Char>,
    ) : StringViolation

    data class ContainsNone(
        override val message: String,
        val chars: Set<Char>,
    ) : StringViolation

    data class ContainsRegex(
        override val message: String,
        val regex: Regex,
    ) : StringViolation

    data class NotContainsRegex(
        override val message: String,
        val regex: Regex,
    ) : StringViolation

    data class Matches(
        override val message: String,
        val regex: Regex,
    ) : StringViolation

    data class NotMatches(
        override val message: String,
        val regex: Regex,
    ) : StringViolation

    data class StartsWith(
        override val message: String,
        val prefix: String,
    ) : StringViolation

    data class EndsWith(
        override val message: String,
        val suffix: String,
    ) : StringViolation

    data class Alphabetic(
        override val message: String,
    ) : StringViolation

    data class Alphanumeric(
        override val message: String,
    ) : StringViolation

    data class NotBlank(
        override val message: String,
    ) : StringViolation

    data class NotEmpty(
        override val message: String,
    ) : StringViolation

    data class LowerCase(
        override val message: String,
    ) : StringViolation

    data class UpperCase(
        override val message: String,
    ) : StringViolation

    data class Numeric(
        override val message: String,
    ) : StringViolation
}
