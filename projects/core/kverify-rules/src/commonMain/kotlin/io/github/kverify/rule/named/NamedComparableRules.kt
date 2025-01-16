package io.github.kverify.rule.named

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.dsl.extension.asViolation
import io.github.kverify.dsl.extension.validate
import io.github.kverify.dsl.model.createNamedRule
import io.github.kverify.rule.localization.DefaultRuleLocalization
import io.github.kverify.rule.localization.RuleLocalization
import io.github.kverify.rule.type.ComparableRuleType

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
                localization
                    .getLocalization(
                        ComparableRuleType.EqualTo(other),
                        namedValue,
                    ).asViolation()
            }
        }

    fun <T : Comparable<T>> notEqualTo(other: T): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value != other,
            ) {
                localization
                    .getLocalization(
                        ComparableRuleType.NotEqualTo(other),
                        namedValue,
                    ).asViolation()
            }
        }

    fun <T : Comparable<T>> greaterThan(other: T): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value > other,
            ) {
                localization
                    .getLocalization(
                        ComparableRuleType.GreaterThan(other),
                        namedValue,
                    ).asViolation()
            }
        }

    fun <T : Comparable<T>> greaterThanOrEqualTo(other: T): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value >= other,
            ) {
                localization
                    .getLocalization(
                        ComparableRuleType.GreaterThanOrEqualTo(other),
                        namedValue,
                    ).asViolation()
            }
        }

    fun <T : Comparable<T>> lessThan(other: T): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value < other,
            ) {
                localization
                    .getLocalization(
                        ComparableRuleType.LessThan(other),
                        namedValue,
                    ).asViolation()
            }
        }

    fun <T : Comparable<T>> lessThanOrEqualTo(other: T): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value <= other,
            ) {
                localization
                    .getLocalization(
                        ComparableRuleType.LessThanOrEqualTo(other),
                        namedValue,
                    ).asViolation()
            }
        }

    fun <T : Comparable<T>> between(
        min: T,
        max: T,
        inclusive: Boolean = true,
    ): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                if (inclusive) {
                    namedValue.value in min..max
                } else {
                    namedValue.value > min && namedValue.value < max
                },
            ) {
                localization
                    .getLocalization(
                        ComparableRuleType.Between(min, max),
                        namedValue,
                    ).asViolation()
            }
        }

    fun <T : Comparable<T>> notBetween(
        min: T,
        max: T,
        inclusive: Boolean = true,
    ): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                if (inclusive) {
                    namedValue.value < min || namedValue.value > max
                } else {
                    namedValue.value <= min || namedValue.value >= max
                },
            ) {
                localization
                    .getLocalization(
                        ComparableRuleType.NotBetween(min, max),
                        namedValue,
                    ).asViolation()
            }
        }
}
