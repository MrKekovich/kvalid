package io.github.kverify.rule.named

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.dsl.extension.validate
import io.github.kverify.dsl.model.createNamedRule
import io.github.kverify.rule.localization.DefaultRuleLocalization
import io.github.kverify.rule.localization.RuleLocalization
import io.github.kverify.rule.type.StringRuleType

@Suppress("TooManyFunctions")
open class NamedStringRules(
    protected val localization: RuleLocalization = DefaultRuleLocalization(),
) {
    fun ofLength(length: Int): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.length == length,
            ) {
                localization.getLocalization(
                    StringRuleType.OfLength(length),
                    namedValue,
                )
            }
        }

    fun notOfLength(length: Int): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.length != length,
            ) {
                localization.getLocalization(
                    StringRuleType.NotOfLength(length),
                    namedValue,
                )
            }
        }

    fun maxLength(max: Int): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.length <= max,
            ) {
                localization.getLocalization(
                    StringRuleType.MaxLength(max),
                    namedValue,
                )
            }
        }

    fun minLength(min: Int): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.length >= min,
            ) {
                localization.getLocalization(
                    StringRuleType.MinLength(min),
                    namedValue,
                )
            }
        }

    fun lengthBetween(range: IntRange): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.length in range,
            ) {
                localization.getLocalization(
                    StringRuleType.LengthBetween(range),
                    namedValue,
                )
            }
        }

    fun lengthNotBetween(range: IntRange): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.length !in range,
            ) {
                localization.getLocalization(
                    StringRuleType.LengthNotBetween(range),
                    namedValue,
                )
            }
        }

    fun lengthBetween(
        min: Int,
        max: Int,
    ): Rule<NamedValue<String>> =
        lengthNotBetween(
            min..max,
        )

    fun lengthNotBetween(
        min: Int,
        max: Int,
    ): Rule<NamedValue<String>> =
        lengthNotBetween(
            min..max,
        )

    fun contains(
        string: String,
        ignoreCase: Boolean = false,
    ): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.contains(string, ignoreCase),
            ) {
                localization.getLocalization(
                    StringRuleType.Contains(string),
                    namedValue,
                )
            }
        }

    fun contains(
        char: Char,
        ignoreCase: Boolean = false,
    ): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.contains(char, ignoreCase),
            ) {
                localization.getLocalization(
                    StringRuleType.Contains(char),
                    namedValue,
                )
            }
        }

    fun contains(regex: Regex): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.contains(regex),
            ) {
                localization.getLocalization(
                    StringRuleType.ContainsRegex(regex),
                    namedValue,
                )
            }
        }

    fun notContains(
        string: String,
        ignoreCase: Boolean = false,
    ): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                !namedValue.value.contains(string, ignoreCase),
            ) {
                localization.getLocalization(
                    StringRuleType.Contains(string),
                    namedValue,
                )
            }
        }

    fun notContains(
        char: Char,
        ignoreCase: Boolean = false,
    ): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                !namedValue.value.contains(char, ignoreCase),
            ) {
                localization.getLocalization(
                    StringRuleType.NotContains(char),
                    namedValue,
                )
            }
        }

    fun containsAll(chars: Set<Char>): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                chars.all { char -> char in namedValue.value },
            ) {
                localization.getLocalization(
                    StringRuleType.ContainsAll(chars),
                    namedValue,
                )
            }
        }

    fun containsOnly(allowedChars: Set<Char>): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { char -> char in allowedChars },
            ) {
                localization.getLocalization(
                    StringRuleType.ContainsOnly(allowedChars),
                    namedValue,
                )
            }
        }

    fun containsNone(prohibitedChars: Set<Char>): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.none { char -> char in prohibitedChars },
            ) {
                localization.getLocalization(
                    StringRuleType.ContainsNone(prohibitedChars),
                    namedValue,
                )
            }
        }

    fun matches(regex: Regex): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                regex.matches(namedValue.value),
            ) {
                localization.getLocalization(
                    StringRuleType.Matches(regex),
                    namedValue,
                )
            }
        }

    fun notMatches(regex: Regex): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                !regex.matches(namedValue.value),
            ) {
                localization.getLocalization(
                    StringRuleType.NotMatches(regex),
                    namedValue,
                )
            }
        }

    fun startsWith(
        prefix: String,
        ignoreCase: Boolean = false,
    ): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.startsWith(prefix, ignoreCase),
            ) {
                localization.getLocalization(
                    StringRuleType.StartsWith(prefix),
                    namedValue,
                )
            }
        }

    fun endsWith(
        suffix: String,
        ignoreCase: Boolean = false,
    ): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.endsWith(suffix, ignoreCase),
            ) {
                localization.getLocalization(
                    StringRuleType.EndsWith(suffix),
                    namedValue,
                )
            }
        }

    fun alphabetic(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { char -> char.isLetter() },
            ) {
                localization.getLocalization(
                    StringRuleType.Alphabetic,
                    namedValue,
                )
            }
        }

    fun alphanumeric(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { char -> char.isLetterOrDigit() },
            ) {
                localization.getLocalization(
                    StringRuleType.Alphanumeric,
                    namedValue,
                )
            }
        }

    fun notBlank(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.isNotBlank(),
            ) {
                localization.getLocalization(
                    StringRuleType.NotBlank,
                    namedValue,
                )
            }
        }

    fun notEmpty(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.isNotEmpty(),
            ) {
                localization.getLocalization(
                    StringRuleType.NotEmpty,
                    namedValue,
                )
            }
        }

    fun lowerCase(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { char -> char.isLowerCase() },
            ) {
                localization.getLocalization(
                    StringRuleType.LowerCase,
                    namedValue,
                )
            }
        }

    fun upperCase(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { char -> char.isUpperCase() },
            ) {
                localization.getLocalization(
                    StringRuleType.UpperCase,
                    namedValue,
                )
            }
        }

    fun numeric(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { char -> char.isDigit() },
            ) {
                localization.getLocalization(
                    StringRuleType.Numeric,
                    namedValue,
                )
            }
        }
}
