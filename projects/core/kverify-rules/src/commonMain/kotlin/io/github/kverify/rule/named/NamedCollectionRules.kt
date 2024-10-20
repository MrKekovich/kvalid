package io.github.kverify.rule.named

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.dsl.model.createNamedRule
import io.github.kverify.rule.CollectionRules
import io.github.kverify.rule.localization.RuleLocalization
import io.github.kverify.rule.type.CollectionRuleType

@Suppress("TooManyFunctions")
class NamedCollectionRules(
    private val localization: RuleLocalization,
) {
    fun ofSize(size: Int): Rule<NamedValue<Collection<*>>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.OfSize(size),
                    namedValue,
                ),
            ) {
                CollectionRules.ofSize(size).invoke(namedValue.value)
            }
        }

    fun notOfSize(size: Int): Rule<NamedValue<Collection<*>>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.NotOfSize(size),
                    namedValue,
                ),
            ) {
                CollectionRules.notOfSize(size).invoke(namedValue.value)
            }
        }

    fun sizeBetween(range: IntRange): Rule<NamedValue<Collection<*>>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.SizeBetween(range),
                    namedValue,
                ),
            ) {
                CollectionRules.sizeBetween(range).invoke(namedValue.value)
            }
        }

    fun sizeNotBetween(range: IntRange): Rule<NamedValue<Collection<*>>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.SizeNotBetween(range),
                    namedValue,
                ),
            ) {
                CollectionRules.sizeNotBetween(range).invoke(namedValue.value)
            }
        }

    fun sizeBetween(
        min: Int,
        max: Int,
    ): Rule<NamedValue<Collection<*>>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.SizeBetween(min, max),
                    namedValue,
                ),
            ) {
                CollectionRules.sizeBetween(min, max).invoke(namedValue.value)
            }
        }

    fun sizeNotBetween(
        min: Int,
        max: Int,
    ): Rule<NamedValue<Collection<*>>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.SizeNotBetween(min, max),
                    namedValue,
                ),
            ) {
                CollectionRules.sizeNotBetween(min, max).invoke(namedValue.value)
            }
        }

    fun <T> containsAll(elements: Collection<T>): Rule<NamedValue<Collection<T>>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.ContainsAll(elements),
                    namedValue,
                ),
            ) {
                CollectionRules.containsAll(elements).invoke(namedValue.value)
            }
        }

    fun containsNone(elements: Collection<*>): Rule<NamedValue<Collection<*>>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.ContainsNone(elements),
                    namedValue,
                ),
            ) {
                CollectionRules.containsNone(elements).invoke(namedValue.value)
            }
        }

    fun <T> contains(element: T): Rule<NamedValue<Collection<T>>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.Contains(element),
                    namedValue,
                ),
            ) {
                CollectionRules.contains(element).invoke(namedValue.value)
            }
        }

    fun <T> notContains(element: T): Rule<NamedValue<Collection<T>>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.NotContains(element),
                    namedValue,
                ),
            ) {
                CollectionRules.notContains(element).invoke(namedValue.value)
            }
        }

    fun <T> containsOnly(elements: Collection<T>): Rule<NamedValue<Collection<T>>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.ContainsOnly(elements),
                    namedValue,
                ),
            ) {
                CollectionRules.containsOnly(elements).invoke(namedValue.value)
            }
        }

    val notEmpty: Rule<NamedValue<Collection<*>>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.NotEmpty,
                    namedValue,
                ),
            ) {
                CollectionRules.notEmpty.invoke(namedValue.value)
            }
        }

    val distinct: Rule<NamedValue<Collection<*>>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.Distinct,
                    namedValue,
                ),
            ) {
                CollectionRules.distinct.invoke(namedValue.value)
            }
        }
}
