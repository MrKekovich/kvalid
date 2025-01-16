package io.github.kverify.rule.named

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.dsl.extension.validate
import io.github.kverify.dsl.model.createNamedRule
import io.github.kverify.rule.localization.DefaultRuleLocalization
import io.github.kverify.rule.localization.RuleLocalization
import io.github.kverify.rule.type.ComparableRuleType
import io.github.kverify.rule.violation.ComparableViolation

@Suppress("TooManyFunctions")
open class NamedComparableRules(
    @Suppress("MemberVisibilityCanBePrivate")
    protected val localization: RuleLocalization = DefaultRuleLocalization(),
) {
    fun <T : Comparable<T>> equalTo(other: T): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value == other,
            ) {
                val message =
                    localization.getLocalization(
                        ComparableRuleType.EqualTo(other = other),
                        namedValue,
                    )

                ComparableViolation.EqualTo(
                    message = message,
                    other = other,
                )
            }
        }

    fun <T : Comparable<T>> notEqualTo(other: T): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value != other,
            ) {
                val message =
                    localization.getLocalization(
                        ComparableRuleType.NotEqualTo(other = other),
                        namedValue,
                    )

                ComparableViolation.NotEqualTo(
                    message = message,
                    other = other,
                )
            }
        }

    fun <T : Comparable<T>> greaterThan(other: T): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value > other,
            ) {
                val message =
                    localization.getLocalization(
                        ComparableRuleType.GreaterThan(other = other),
                        namedValue,
                    )

                ComparableViolation.GreaterThan(
                    message = message,
                    other = other,
                )
            }
        }

    fun <T : Comparable<T>> greaterThanOrEqualTo(other: T): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value >= other,
            ) {
                val message =
                    localization.getLocalization(
                        ComparableRuleType.GreaterThanOrEqualTo(other = other),
                        namedValue,
                    )

                ComparableViolation.GreaterThanOrEqualTo(
                    message = message,
                    other = other,
                )
            }
        }

    fun <T : Comparable<T>> lessThan(other: T): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value < other,
            ) {
                val message =
                    localization.getLocalization(
                        ComparableRuleType.LessThan(other = other),
                        namedValue,
                    )

                ComparableViolation.LessThan(
                    message = message,
                    other = other,
                )
            }
        }

    fun <T : Comparable<T>> lessThanOrEqualTo(other: T): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value <= other,
            ) {
                val message =
                    localization.getLocalization(
                        ComparableRuleType.LessThanOrEqualTo(other = other),
                        namedValue,
                    )

                ComparableViolation.LessThanOrEqualTo(
                    message = message,
                    other = other,
                )
            }
        }

    fun <T : Comparable<T>> between(
        lower: T,
        upper: T,
    ): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value in upper..lower,
            ) {
                val message =
                    localization.getLocalization(
                        ComparableRuleType.Between(
                            lower = lower,
                            upper = upper,
                        ),
                        namedValue,
                    )

                ComparableViolation.Between(
                    message = message,
                    lower = lower,
                    upper = upper,
                )
            }
        }

    fun <T : Comparable<T>> between(range: ClosedRange<T>): Rule<NamedValue<T>> =
        between(
            range.start,
            range.endInclusive,
        )

    fun <T : Comparable<T>> notBetween(
        lower: T,
        upper: T,
    ): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value !in lower..upper,
            ) {
                val message =
                    localization.getLocalization(
                        ComparableRuleType.NotBetween(lower, upper),
                        namedValue,
                    )

                ComparableViolation.NotBetween(
                    message = message,
                    lower = lower,
                    upper = upper,
                )
            }
        }

    fun <T : Comparable<T>> notBetween(range: ClosedRange<T>): Rule<NamedValue<T>> =
        notBetween(
            range.start,
            range.endInclusive,
        )
}
