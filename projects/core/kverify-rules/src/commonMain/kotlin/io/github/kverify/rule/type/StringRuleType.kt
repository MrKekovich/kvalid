package io.github.kverify.rule.type

sealed interface StringRuleType : RuleType {
    data class OfLength(
        val length: Int,
    ) : StringRuleType

    data class NotOfLength(
        val length: Int,
    ) : StringRuleType

    data class MinLength(
        val min: Int,
    ) : StringRuleType

    data class MaxLength(
        val max: Int,
    ) : StringRuleType

    data class LengthBetween(
        val range: IntRange,
    ) : StringRuleType {
        constructor(min: Int, max: Int) : this(min..max)
    }

    data class LengthNotBetween(
        val range: IntRange,
    ) : StringRuleType {
        constructor(min: Int, max: Int) : this(min..max)
    }

    data class Contains(
        val string: String,
    ) : StringRuleType {
        constructor(char: Char) : this(char.toString())
    }

    data class ContainsRegex(
        val regex: Regex,
    ) : StringRuleType

    data class NotContains(
        val string: String,
    ) : StringRuleType {
        constructor(char: Char) : this(char.toString())
    }

    data class ContainsAll(
        val chars: Set<Char>,
    ) : StringRuleType

    data class ContainsOnly(
        val chars: Set<Char>,
    ) : StringRuleType

    data class ContainsNone(
        val chars: Set<Char>,
    ) : StringRuleType

    data class Matches(
        val regex: Regex,
    ) : StringRuleType

    data class NotMatches(
        val regex: Regex,
    ) : StringRuleType

    data class StartsWith(
        val prefix: String,
    ) : StringRuleType

    data class EndsWith(
        val suffix: String,
    ) : StringRuleType

    data object Alphabetic : StringRuleType

    data object Alphanumeric : StringRuleType

    data object NotBlank : StringRuleType

    data object NotEmpty : StringRuleType

    data object LowerCase : StringRuleType

    data object UpperCase : StringRuleType

    data object Numeric : StringRuleType
}
