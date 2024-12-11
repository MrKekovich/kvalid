package io.github.kverify.rule.named

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.dsl.model.createNamedRule
import io.github.kverify.rule.CollectionRules
import io.github.kverify.rule.localization.DefaultRuleLocalization
import io.github.kverify.rule.localization.RuleLocalization
import io.github.kverify.rule.type.CollectionRuleType

@Suppress("TooManyFunctions")
open class NamedCollectionRules(
    protected val localization: RuleLocalization = DefaultRuleLocalization(),
) {
    fun <C : Collection<*>> ofSize(size: Int): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.OfSize(size),
                    namedValue,
                ),
            ) {
                CollectionRules.ofSize<C>(size).invoke(namedValue.value)
            }
        }

    fun <C : Collection<*>> notOfSize(size: Int): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.NotOfSize(size),
                    namedValue,
                ),
            ) {
                CollectionRules.notOfSize<C>(size).invoke(namedValue.value)
            }
        }

    fun <C : Collection<*>> sizeBetween(range: IntRange): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.SizeBetween(range),
                    namedValue,
                ),
            ) {
                CollectionRules.sizeBetween<C>(range).invoke(namedValue.value)
            }
        }

    fun <C : Collection<*>> sizeNotBetween(range: IntRange): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.SizeNotBetween(range),
                    namedValue,
                ),
            ) {
                CollectionRules.sizeNotBetween<C>(range).invoke(namedValue.value)
            }
        }

    fun <C : Collection<*>> sizeBetween(
        min: Int,
        max: Int,
    ): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.SizeBetween(min, max),
                    namedValue,
                ),
            ) {
                CollectionRules.sizeBetween<C>(min, max).invoke(namedValue.value)
            }
        }

    fun <C : Collection<*>> sizeNotBetween(
        min: Int,
        max: Int,
    ): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.SizeNotBetween(min, max),
                    namedValue,
                ),
            ) {
                CollectionRules.sizeNotBetween<C>(min, max).invoke(namedValue.value)
            }
        }

    fun <T, C : Collection<T>> containsAll(elements: C): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.ContainsAll(elements),
                    namedValue,
                ),
            ) {
                CollectionRules.containsAll<T, C>(elements).invoke(namedValue.value)
            }
        }

    fun <T, C : Collection<T>> containsNone(elements: C): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.ContainsNone(elements),
                    namedValue,
                ),
            ) {
                CollectionRules.containsNone<T, C>(elements).invoke(namedValue.value)
            }
        }

    fun <T, C : Collection<T>> contains(element: T): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.Contains(element),
                    namedValue,
                ),
            ) {
                CollectionRules.contains<T, C>(element).invoke(namedValue.value)
            }
        }

    fun <T, C : Collection<T>> notContains(element: T): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.NotContains(element),
                    namedValue,
                ),
            ) {
                CollectionRules.notContains<T, C>(element).invoke(namedValue.value)
            }
        }

    fun <T, C : Collection<T>> containsOnly(elements: Collection<T>): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.ContainsOnly(elements),
                    namedValue,
                ),
            ) {
                CollectionRules.containsOnly<T, C>(elements).invoke(namedValue.value)
            }
        }

    fun <C : Collection<*>> notEmpty(): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.NotEmpty,
                    namedValue,
                ),
            ) {
                CollectionRules.notEmpty<C>().invoke(namedValue.value)
            }
        }

    fun <C : Collection<*>> distinct(): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                localization.getLocalization(
                    CollectionRuleType.Distinct,
                    namedValue,
                ),
            ) {
                CollectionRules.distinct<C>().invoke(namedValue.value)
            }
        }
}
