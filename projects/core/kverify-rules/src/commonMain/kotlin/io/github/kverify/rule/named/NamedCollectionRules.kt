package io.github.kverify.rule.named

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.dsl.extension.validate
import io.github.kverify.dsl.model.createNamedRule
import io.github.kverify.rule.localization.DefaultRuleLocalization
import io.github.kverify.rule.localization.RuleLocalization
import io.github.kverify.rule.type.CollectionRuleType

@Suppress("TooManyFunctions")
open class NamedCollectionRules(
    @Suppress("MemberVisibilityCanBePrivate")
    protected val localization: RuleLocalization = DefaultRuleLocalization(),
) {
    fun <C : Collection<*>> ofSize(size: Int): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.size == size,
            ) {
                localization.getLocalization(
                    CollectionRuleType.OfSize(size),
                    namedValue,
                )
            }
        }

    fun <C : Collection<*>> notOfSize(size: Int): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.size != size,
            ) {
                localization.getLocalization(
                    CollectionRuleType.NotOfSize(size),
                    namedValue,
                )
            }
        }

    fun <C : Collection<*>> sizeBetween(range: IntRange): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.size in range,
            ) {
                localization.getLocalization(
                    CollectionRuleType.SizeBetween(range),
                    namedValue,
                )
            }
        }

    fun <C : Collection<*>> sizeNotBetween(range: IntRange): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.size !in range,
            ) {
                localization.getLocalization(
                    CollectionRuleType.SizeNotBetween(range),
                    namedValue,
                )
            }
        }

    fun <C : Collection<*>> sizeBetween(
        min: Int,
        max: Int,
    ): Rule<NamedValue<C>> =
        sizeBetween(
            min..max,
        )

    fun <C : Collection<*>> sizeNotBetween(
        min: Int,
        max: Int,
    ): Rule<NamedValue<C>> =
        sizeNotBetween(
            min..max,
        )

    fun <T, C : Collection<T>> containsAll(elements: C): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.containsAll(elements),
            ) {
                localization.getLocalization(
                    CollectionRuleType.ContainsAll(elements),
                    namedValue,
                )
            }
        }

    fun <T, C : Collection<T>> containsNone(elements: C): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                elements.none { it in namedValue.value },
            ) {
                localization.getLocalization(
                    CollectionRuleType.ContainsNone(elements),
                    namedValue,
                )
            }
        }

    fun <T, C : Collection<T>> contains(element: T): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.contains(element),
            ) {
                localization.getLocalization(
                    CollectionRuleType.Contains(element),
                    namedValue,
                )
            }
        }

    fun <T, C : Collection<T>> notContains(element: T): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                !namedValue.value.contains(element),
            ) {
                localization.getLocalization(
                    CollectionRuleType.NotContains(element),
                    namedValue,
                )
            }
        }

    fun <T, C : Collection<T>> containsOnly(allowedElements: Collection<T>): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { element -> element in allowedElements },
            ) {
                localization.getLocalization(
                    CollectionRuleType.ContainsOnly(allowedElements),
                    namedValue,
                )
            }
        }

    fun <C : Collection<*>> notEmpty(): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.isNotEmpty(),
            ) {
                localization.getLocalization(
                    CollectionRuleType.NotEmpty,
                    namedValue,
                )
            }
        }

    fun <C : Collection<*>> distinct(): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.size == namedValue.value.toSet().size,
            ) {
                localization.getLocalization(
                    CollectionRuleType.Distinct,
                    namedValue,
                )
            }
        }
}
