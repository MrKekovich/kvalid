package io.github.kverify.rule.named

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.dsl.extension.validate
import io.github.kverify.dsl.model.createNamedRule
import io.github.kverify.rule.localization.DefaultRuleLocalization
import io.github.kverify.rule.localization.RuleLocalization
import io.github.kverify.rule.type.StringRuleType
import io.github.kverify.rule.violation.StringViolation

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
                val message =
                    localization.getLocalization(
                        StringRuleType.OfLength(
                            length = length,
                        ),
                        namedValue,
                    )

                StringViolation.OfLength(
                    message = message,
                    length = length,
                )
            }
        }

    fun notOfLength(length: Int): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.length != length,
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.NotOfLength(
                            length = length,
                        ),
                        namedValue,
                    )

                StringViolation.NotOfLength(
                    message = message,
                    length = length,
                )
            }
        }

    fun maxLength(max: Int): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.length <= max,
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.MaxLength(
                            max = max,
                        ),
                        namedValue,
                    )

                StringViolation.MaxLength(
                    message = message,
                    max = max,
                )
            }
        }

    fun minLength(min: Int): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.length >= min,
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.MinLength(
                            min = min,
                        ),
                        namedValue,
                    )

                StringViolation.MinLength(
                    message = message,
                    min = min,
                )
            }
        }

    fun lengthBetween(range: IntRange): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.length in range,
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.LengthBetween(
                            range = range,
                        ),
                        namedValue,
                    )

                StringViolation.LengthBetween(
                    message = message,
                    range,
                )
            }
        }

    fun lengthNotBetween(range: IntRange): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.length !in range,
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.LengthNotBetween(
                            range = range,
                        ),
                        namedValue,
                    )

                StringViolation.LengthNotBetween(
                    message = message,
                    range,
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
                val message =
                    localization.getLocalization(
                        StringRuleType.Contains(
                            string = string,
                        ),
                        namedValue,
                    )

                StringViolation.Contains(
                    message = message,
                    string,
                )
            }
        }

    fun contains(regex: Regex): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.contains(regex),
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.ContainsRegex(
                            regex = regex,
                        ),
                        namedValue,
                    )

                StringViolation.ContainsRegex(
                    message = message,
                    regex,
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
                val message =
                    localization.getLocalization(
                        StringRuleType.Contains(
                            string = string,
                        ),
                        namedValue,
                    )

                StringViolation.Contains(
                    message = message,
                    string,
                )
            }
        }

    fun notContains(regex: Regex): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                !namedValue.value.contains(regex),
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.ContainsRegex(
                            regex = regex,
                        ),
                        namedValue,
                    )

                StringViolation.NotContainsRegex(
                    message = message,
                    regex,
                )
            }
        }

    fun containsAll(chars: Set<Char>): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                chars.all { char -> char in namedValue.value },
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.ContainsAll(chars = chars),
                        namedValue,
                    )

                StringViolation.ContainsAll(
                    message = message,
                    chars,
                )
            }
        }

    fun containsOnly(chars: Set<Char>): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { char -> char in chars },
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.ContainsOnly(
                            chars = chars,
                        ),
                        namedValue,
                    )

                StringViolation.ContainsOnly(
                    message = message,
                    chars,
                )
            }
        }

    fun containsNone(chars: Set<Char>): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.none { char -> char in chars },
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.ContainsNone(
                            chars = chars,
                        ),
                        namedValue,
                    )

                StringViolation.ContainsNone(
                    message = message,
                    chars,
                )
            }
        }

    fun matches(regex: Regex): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                regex.matches(namedValue.value),
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.Matches(
                            regex = regex,
                        ),
                        namedValue,
                    )

                StringViolation.Matches(
                    message = message,
                    regex,
                )
            }
        }

    fun notMatches(regex: Regex): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                !regex.matches(namedValue.value),
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.NotMatches(
                            regex = regex,
                        ),
                        namedValue,
                    )

                StringViolation.NotMatches(
                    message = message,
                    regex,
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
                val message =
                    localization.getLocalization(
                        StringRuleType.StartsWith(
                            prefix = prefix,
                        ),
                        namedValue,
                    )

                StringViolation.StartsWith(
                    message = message,
                    prefix,
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
                val message =
                    localization.getLocalization(
                        StringRuleType.EndsWith(
                            suffix = suffix,
                        ),
                        namedValue,
                    )

                StringViolation.EndsWith(
                    message = message,
                    suffix,
                )
            }
        }

    fun alphabetic(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { char -> char.isLetter() },
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.Alphabetic,
                        namedValue,
                    )

                StringViolation.Alphabetic(
                    message = message,
                )
            }
        }

    fun alphanumeric(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { char -> char.isLetterOrDigit() },
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.Alphanumeric,
                        namedValue,
                    )

                StringViolation.Alphanumeric(
                    message = message,
                )
            }
        }

    fun notBlank(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.isNotBlank(),
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.NotBlank,
                        namedValue,
                    )

                StringViolation.NotBlank(
                    message = message,
                )
            }
        }

    fun notEmpty(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.isNotEmpty(),
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.NotEmpty,
                        namedValue,
                    )

                StringViolation.NotEmpty(
                    message = message,
                )
            }
        }

    fun lowerCase(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { char -> char.isLowerCase() },
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.LowerCase,
                        namedValue,
                    )

                StringViolation.LowerCase(
                    message = message,
                )
            }
        }

    fun upperCase(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { char -> char.isUpperCase() },
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.UpperCase,
                        namedValue,
                    )

                StringViolation.UpperCase(
                    message = message,
                )
            }
        }

    fun numeric(): Rule<NamedValue<String>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { char -> char.isDigit() },
            ) {
                val message =
                    localization.getLocalization(
                        StringRuleType.Numeric,
                        namedValue,
                    )

                StringViolation.Numeric(
                    message = message,
                )
            }
        }
}
