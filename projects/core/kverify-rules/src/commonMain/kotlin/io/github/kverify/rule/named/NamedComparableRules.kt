package io.github.kverify.rule.named

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.dsl.extension.validate
import io.github.kverify.dsl.model.createNamedRule
import io.github.kverify.rule.ComparableRules
import io.github.kverify.rule.localization.DefaultRuleLocalization
import io.github.kverify.rule.localization.RuleLocalization
import io.github.kverify.rule.type.ComparableRuleType

@Suppress("TooManyFunctions")
open class NamedComparableRules(
    protected val localization: RuleLocalization = DefaultRuleLocalization(),
) {
    fun <T : Comparable<T>> equalTo(other: T): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    ComparableRuleType.EqualTo(other),
                    namedValue,
                ),
            ) {
                ComparableRules.equalTo(other).invoke(namedValue.value)
            }
        }

    fun <T : Comparable<T>> notEqualTo(other: T): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    ComparableRuleType.NotEqualTo(other),
                    namedValue,
                ),
            ) {
                ComparableRules.notEqualTo(other).invoke(namedValue.value)
            }
        }

    fun <T : Comparable<T>> greaterThan(other: T): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    ComparableRuleType.GreaterThan(other),
                    namedValue,
                ),
            ) {
                ComparableRules.greaterThan(other).invoke(namedValue.value)
            }
        }

    fun <T : Comparable<T>> greaterThanOrEqualTo(other: T): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    ComparableRuleType.GreaterThanOrEqualTo(other),
                    namedValue,
                ),
            ) {
                ComparableRules.greaterThanOrEqualTo(other).invoke(namedValue.value)
            }
        }

    fun <T : Comparable<T>> lessThan(other: T): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    ComparableRuleType.LessThan(other),
                    namedValue,
                ),
            ) {
                ComparableRules.lessThan(other).invoke(namedValue.value)
            }
        }

    fun <T : Comparable<T>> lessThanOrEqualTo(other: T): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    ComparableRuleType.LessThanOrEqualTo(other),
                    namedValue,
                ),
            ) {
                ComparableRules.lessThanOrEqualTo(other).invoke(namedValue.value)
            }
        }

    fun <T : Comparable<T>> between(
        lower: T,
        upper: T,
    ): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    ComparableRuleType.Between(lower, upper),
                    namedValue,
                ),
            ) {
                ComparableRules.between(lower, upper).invoke(namedValue.value)
            }
        }

    fun <T : Comparable<T>> notBetween(
        lower: T,
        upper: T,
    ): Rule<NamedValue<T>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    ComparableRuleType.NotBetween(lower, upper),
                    namedValue,
                ),
            ) {
                ComparableRules.notBetween(lower, upper).invoke(namedValue.value)
            }
        }
}
