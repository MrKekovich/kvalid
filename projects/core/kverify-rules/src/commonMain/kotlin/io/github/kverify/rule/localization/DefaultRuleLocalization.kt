package io.github.kverify.rule.localization

import io.github.kverify.core.model.NamedValue
import io.github.kverify.rule.type.CollectionRuleType
import io.github.kverify.rule.type.ComparableRuleType
import io.github.kverify.rule.type.RuleType
import io.github.kverify.rule.type.StringRuleType

class DefaultRuleLocalization : RuleLocalization {
    override fun getLocalization(
        key: RuleType,
        namedValue: NamedValue<*>,
    ): String =
        when (key) {
            is CollectionRuleType -> namedValue.getCollectionLocalization(key)
            is ComparableRuleType -> namedValue.getComparableLocalization(key)
            is StringRuleType -> namedValue.getStringLocalization(key)
            else -> error("Unsupported rule type: $key")
        }

    private fun NamedValue<*>.getCollectionLocalization(key: CollectionRuleType): String =
        when (key) {
            is CollectionRuleType.OfSize -> "'$name' must be of size ${key.size}"
            is CollectionRuleType.NotOfSize -> "'$name' must not be of size ${key.size}"
            is CollectionRuleType.SizeBetween -> "'$name' must be of size between ${key.range}"
            is CollectionRuleType.SizeNotBetween -> "'$name' must not be of size between ${key.range}"
            is CollectionRuleType.ContainsAll<*> ->
                "'$name' must contain all of the following elements: ${key.elements}"
            is CollectionRuleType.ContainsNone<*> ->
                "'$name' must not contain any of the following elements: ${key.elements}"
            is CollectionRuleType.Contains<*> -> "'$name' must contain ${key.element}"
            is CollectionRuleType.NotContains<*> -> "'$name' must not contain ${key.element}"
            is CollectionRuleType.ContainsOnly<*> -> "'$name' must contain only the following elements: ${key.elements}"
            is CollectionRuleType.NotEmpty -> "'$name' must not be empty"
            is CollectionRuleType.Distinct -> "'$name' must be distinct"
        }

    private fun NamedValue<*>.getComparableLocalization(key: ComparableRuleType): String =
        when (key) {
            is ComparableRuleType.EqualTo<*> -> "'$name' must be equal to ${key.other}"
            is ComparableRuleType.NotEqualTo<*> -> "'$name' must not be equal to ${key.other}"
            is ComparableRuleType.GreaterThan<*> -> "'$name' must be greater than ${key.other}"
            is ComparableRuleType.GreaterThanOrEqualTo<*> -> "'$name' must be greater than or equal to ${key.other}"
            is ComparableRuleType.LessThan<*> -> "'$name' must be less than ${key.other}"
            is ComparableRuleType.LessThanOrEqualTo<*> -> "'$name' must be less than or equal to ${key.other}"
            is ComparableRuleType.Between<*> -> "'$name' must be between ${key.lower} and ${key.upper}"
            is ComparableRuleType.NotBetween<*> -> "'$name' must not be between ${key.lower} and ${key.upper}"
        }

    @Suppress("CyclomaticComplexMethod")
    private fun NamedValue<*>.getStringLocalization(key: StringRuleType): String =
        when (key) {
            is StringRuleType.OfLength -> "'$name' must be of length ${key.length}"
            is StringRuleType.NotOfLength -> "'$name' must not be of length ${key.length}"
            is StringRuleType.MinLength -> "'$name' must be at least ${key.min} characters long"
            is StringRuleType.MaxLength -> "'$name' must be at most ${key.max} characters long"
            is StringRuleType.LengthBetween -> "'$name' must be of length between ${key.range}"
            is StringRuleType.LengthNotBetween -> "'$name' must not be of length between ${key.range}"
            is StringRuleType.Contains -> "'$name' must contain ${key.string}"
            is StringRuleType.NotContains -> "'$name' must not contain ${key.string}"
            is StringRuleType.ContainsOnly -> "'$name' must contain only following characters: ${key.chars}"
            is StringRuleType.ContainsAll -> "'$name' must contain all characters: ${key.chars}"
            is StringRuleType.ContainsNone -> "'$name' must not contain any of the following characters: ${key.chars}"
            is StringRuleType.ContainsRegex -> "'$name' must contain ${key.regex.pattern}"
            is StringRuleType.NotContainsRegex -> "'$name' must not contain ${key.regex.pattern}"
            is StringRuleType.Matches -> "'$name' must match the regex ${key.regex.pattern}"
            is StringRuleType.NotMatches -> "'$name' must not match the regex ${key.regex.pattern}"
            is StringRuleType.StartsWith -> "'$name' must start with ${key.prefix}"
            is StringRuleType.EndsWith -> "'$name' must end with ${key.suffix}"
            is StringRuleType.Alphabetic -> "'$name' must be alphabetic"
            is StringRuleType.Alphanumeric -> "'$name' must be alphanumeric"
            is StringRuleType.Numeric -> "'$name' must be numeric"
            is StringRuleType.NotBlank -> "'$name' must not be blank"
            is StringRuleType.NotEmpty -> "'$name' must not be empty"
            is StringRuleType.LowerCase -> "'$name' must be lower case"
            is StringRuleType.UpperCase -> "'$name' must be upper case"
        }
}
