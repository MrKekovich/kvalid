package io.github.kverify.rule.named

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.dsl.model.createNamedRule
import io.github.kverify.rule.StringRules
import io.github.kverify.rule.localization.DefaultRuleLocalization
import io.github.kverify.rule.localization.RuleLocalization
import io.github.kverify.rule.type.StringRuleType

@Suppress("TooManyFunctions")
open class NamedStringRules(
    private val localization: RuleLocalization = DefaultRuleLocalization(),
) {
    fun ofLength(length: Int): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.OfLength(length),
                    namedValue,
                ),
            ) {
                StringRules.ofLength(length).invoke(namedValue.value)
            }
        }

    fun notOfLength(length: Int): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.NotOfLength(length),
                    namedValue,
                ),
            ) {
                StringRules.notOfLength(length).invoke(namedValue.value)
            }
        }

    fun maxLength(max: Int): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.MaxLength(max),
                    namedValue,
                ),
            ) {
                StringRules.maxLength(max).invoke(namedValue.value)
            }
        }

    fun minLength(min: Int): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.MinLength(min),
                    namedValue,
                ),
            ) {
                StringRules.minLength(min).invoke(namedValue.value)
            }
        }

    fun lengthBetween(range: IntRange): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.LengthBetween(range),
                    namedValue,
                ),
            ) {
                StringRules.lengthBetween(range).invoke(namedValue.value)
            }
        }

    fun lengthNotBetween(range: IntRange): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.LengthNotBetween(range),
                    namedValue,
                ),
            ) {
                StringRules.lengthNotBetween(range).invoke(namedValue.value)
            }
        }

    fun lengthBetween(
        min: Int,
        max: Int,
    ): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.LengthBetween(min, max),
                    namedValue,
                ),
            ) {
                StringRules.lengthBetween(min, max).invoke(namedValue.value)
            }
        }

    fun lengthNotBetween(
        min: Int,
        max: Int,
    ): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.LengthNotBetween(min, max),
                    namedValue,
                ),
            ) {
                StringRules.lengthNotBetween(min, max).invoke(namedValue.value)
            }
        }

    fun contains(string: String): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.Contains(string),
                    namedValue,
                ),
            ) {
                StringRules.contains(string).invoke(namedValue.value)
            }
        }

    fun notContains(string: String): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.Contains(string),
                    namedValue,
                ),
            ) {
                StringRules.notContains(string).invoke(namedValue.value)
            }
        }

    fun contains(char: Char): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.Contains(char),
                    namedValue,
                ),
            ) {
                StringRules.contains(char).invoke(namedValue.value)
            }
        }

    fun notContains(char: Char): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.NotContains(char),
                    namedValue,
                ),
            ) {
                StringRules.notContains(char).invoke(namedValue.value)
            }
        }

    fun containsAll(chars: Set<Char>): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.ContainsAll(chars),
                    namedValue,
                ),
            ) {
                StringRules.containsAll(chars).invoke(namedValue.value)
            }
        }

    fun containsOnly(chars: Set<Char>): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.ContainsOnly(chars),
                    namedValue,
                ),
            ) {
                StringRules.containsOnly(chars).invoke(namedValue.value)
            }
        }

    fun containsNone(chars: Set<Char>): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.ContainsNone(chars),
                    namedValue,
                ),
            ) {
                StringRules.containsNone(chars).invoke(namedValue.value)
            }
        }

    fun matches(regex: Regex): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.Matches(regex),
                    namedValue,
                ),
            ) {
                StringRules.matches(regex).invoke(namedValue.value)
            }
        }

    fun notMatches(regex: Regex): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.NotMatches(regex),
                    namedValue,
                ),
            ) {
                StringRules.notMatches(regex).invoke(namedValue.value)
            }
        }

    fun startsWith(prefix: String): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.StartsWith(prefix),
                    namedValue,
                ),
            ) {
                StringRules.startsWith(prefix).invoke(namedValue.value)
            }
        }

    fun endsWith(suffix: String): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.EndsWith(suffix),
                    namedValue,
                ),
            ) {
                StringRules.endsWith(suffix).invoke(namedValue.value)
            }
        }

    fun alphabetic(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.Alphabetic,
                    namedValue,
                ),
            ) {
                StringRules.alphabetic().invoke(namedValue.value)
            }
        }

    fun alphanumeric(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.Alphanumeric,
                    namedValue,
                ),
            ) {
                StringRules.alphanumeric().invoke(namedValue.value)
            }
        }

    fun notBlank(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.NotBlank,
                    namedValue,
                ),
            ) {
                StringRules.notBlank().invoke(namedValue.value)
            }
        }

    fun notEmpty(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.NotEmpty,
                    namedValue,
                ),
            ) {
                StringRules.notEmpty().invoke(namedValue.value)
            }
        }

    fun lowerCase(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.LowerCase,
                    namedValue,
                ),
            ) {
                StringRules.lowerCase().invoke(namedValue.value)
            }
        }

    fun upperCase(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.UpperCase,
                    namedValue,
                ),
            ) {
                StringRules.upperCase().invoke(namedValue.value)
            }
        }

    fun numeric(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    StringRuleType.Numeric,
                    namedValue,
                ),
            ) {
                StringRules.numeric().invoke(namedValue.value)
            }
        }
}
