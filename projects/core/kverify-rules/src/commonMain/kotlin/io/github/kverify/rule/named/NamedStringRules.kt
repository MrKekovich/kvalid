package io.github.kverify.rule.named

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.dsl.extension.asViolation
import io.github.kverify.dsl.extension.validate
import io.github.kverify.dsl.model.createNamedRule
import io.github.kverify.rule.localization.DefaultRuleLocalization
import io.github.kverify.rule.localization.RuleLocalization
import io.github.kverify.rule.type.StringRuleType

@Suppress("TooManyFunctions")
open class NamedStringRules(
    @Suppress("MemberVisibilityCanBePrivate")
    protected val localization: RuleLocalization = DefaultRuleLocalization(),
) {
    fun ofLength(length: Int): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.length == length,
            ) {
                localization
                    .getLocalization(
                        StringRuleType.OfLength(length),
                        namedValue,
                    ).asViolation()
            }
        }

    fun notOfLength(length: Int): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.length != length,
            ) {
                localization
                    .getLocalization(
                        StringRuleType.NotOfLength(length),
                        namedValue,
                    ).asViolation()
            }
        }

    fun maxLength(max: Int): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.length <= max,
            ) {
                localization
                    .getLocalization(
                        StringRuleType.MaxLength(max),
                        namedValue,
                    ).asViolation()
            }
        }

    fun minLength(min: Int): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.length >= min,
            ) {
                localization
                    .getLocalization(
                        StringRuleType.MinLength(min),
                        namedValue,
                    ).asViolation()
            }
        }

    fun lengthBetween(range: IntRange): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.length in range,
            ) {
                localization
                    .getLocalization(
                        StringRuleType.LengthBetween(range),
                        namedValue,
                    ).asViolation()
            }
        }

    fun lengthNotBetween(range: IntRange): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.length !in range,
            ) {
                localization
                    .getLocalization(
                        StringRuleType.LengthNotBetween(range),
                        namedValue,
                    ).asViolation()
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
                localization
                    .getLocalization(
                        StringRuleType.Contains(string),
                        namedValue,
                    ).asViolation()
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
                localization
                    .getLocalization(
                        StringRuleType.Contains(char),
                        namedValue,
                    ).asViolation()
            }
        }

    fun contains(regex: Regex): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.contains(regex),
            ) {
                localization
                    .getLocalization(
                        StringRuleType.ContainsRegex(regex),
                        namedValue,
                    ).asViolation()
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
                localization
                    .getLocalization(
                        StringRuleType.Contains(string),
                        namedValue,
                    ).asViolation()
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
                localization
                    .getLocalization(
                        StringRuleType.NotContains(char),
                        namedValue,
                    ).asViolation()
            }
        }

    fun containsAll(chars: Set<Char>): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                chars.all { char -> char in namedValue.value },
            ) {
                localization
                    .getLocalization(
                        StringRuleType.ContainsAll(chars),
                        namedValue,
                    ).asViolation()
            }
        }

    fun containsOnly(allowedChars: Set<Char>): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { char -> char in allowedChars },
            ) {
                localization
                    .getLocalization(
                        StringRuleType.ContainsOnly(allowedChars),
                        namedValue,
                    ).asViolation()
            }
        }

    fun containsNone(prohibitedChars: Set<Char>): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.none { char -> char in prohibitedChars },
            ) {
                localization
                    .getLocalization(
                        StringRuleType.ContainsNone(prohibitedChars),
                        namedValue,
                    ).asViolation()
            }
        }

    fun matches(regex: Regex): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                regex.matches(namedValue.value),
            ) {
                localization
                    .getLocalization(
                        StringRuleType.Matches(regex),
                        namedValue,
                    ).asViolation()
            }
        }

    fun notMatches(regex: Regex): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                !regex.matches(namedValue.value),
            ) {
                localization
                    .getLocalization(
                        StringRuleType.NotMatches(regex),
                        namedValue,
                    ).asViolation()
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
                localization
                    .getLocalization(
                        StringRuleType.StartsWith(prefix),
                        namedValue,
                    ).asViolation()
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
                localization
                    .getLocalization(
                        StringRuleType.EndsWith(suffix),
                        namedValue,
                    ).asViolation()
            }
        }

    fun alphabetic(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { char -> char.isLetter() },
            ) {
                localization
                    .getLocalization(
                        StringRuleType.Alphabetic,
                        namedValue,
                    ).asViolation()
            }
        }

    fun alphanumeric(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { char -> char.isLetterOrDigit() },
            ) {
                localization
                    .getLocalization(
                        StringRuleType.Alphanumeric,
                        namedValue,
                    ).asViolation()
            }
        }

    fun notBlank(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.isNotBlank(),
            ) {
                localization
                    .getLocalization(
                        StringRuleType.NotBlank,
                        namedValue,
                    ).asViolation()
            }
        }

    fun notEmpty(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.isNotEmpty(),
            ) {
                localization
                    .getLocalization(
                        StringRuleType.NotEmpty,
                        namedValue,
                    ).asViolation()
            }
        }

    fun lowerCase(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { char -> char.isLowerCase() },
            ) {
                localization
                    .getLocalization(
                        StringRuleType.LowerCase,
                        namedValue,
                    ).asViolation()
            }
        }

    fun upperCase(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { char -> char.isUpperCase() },
            ) {
                localization
                    .getLocalization(
                        StringRuleType.UpperCase,
                        namedValue,
                    ).asViolation()
            }
        }

    fun numeric(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { char -> char.isDigit() },
            ) {
                localization
                    .getLocalization(
                        StringRuleType.Numeric,
                        namedValue,
                    ).asViolation()
            }
        }
}
