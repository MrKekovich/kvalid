package io.github.kverify.rule.named

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.dsl.extension.validate
import io.github.kverify.dsl.model.createNamedRule
import io.github.kverify.rule.localization.DefaultRuleLocalization
import io.github.kverify.rule.localization.RuleLocalization
import io.github.kverify.rule.type.CollectionRuleType
import io.github.kverify.rule.violation.CollectionViolation

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
                val message =
                    localization.getLocalization(
                        CollectionRuleType.OfSize(
                            size = size,
                        ),
                        namedValue,
                    )

                CollectionViolation.OfSize(
                    message = message,
                    size = size,
                )
            }
        }

    fun <C : Collection<*>> notOfSize(size: Int): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.size != size,
            ) {
                val message =
                    localization.getLocalization(
                        CollectionRuleType.NotOfSize(
                            size = size,
                        ),
                        namedValue,
                    )

                CollectionViolation.NotOfSize(
                    message = message,
                    size = size,
                )
            }
        }

    fun <C : Collection<*>> sizeBetween(range: IntRange): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.size in range,
            ) {
                val message =
                    localization.getLocalization(
                        CollectionRuleType.SizeBetween(
                            range = range,
                        ),
                        namedValue,
                    )

                CollectionViolation.SizeBetween(
                    message = message,
                    range = range,
                )
            }
        }

    fun <C : Collection<*>> sizeNotBetween(range: IntRange): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.size !in range,
            ) {
                val message =
                    localization.getLocalization(
                        CollectionRuleType.SizeNotBetween(
                            range = range,
                        ),
                        namedValue,
                    )

                CollectionViolation.SizeNotBetween(
                    message = message,
                    range = range,
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
                val message =
                    localization.getLocalization(
                        CollectionRuleType.ContainsAll(
                            elements = elements,
                        ),
                        namedValue,
                    )

                CollectionViolation.ContainsAll(
                    message = message,
                    elements = elements,
                )
            }
        }

    fun <T, C : Collection<T>> containsNone(elements: C): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                elements.none { it in namedValue.value },
            ) {
                val message =
                    localization.getLocalization(
                        CollectionRuleType.ContainsNone(
                            elements = elements,
                        ),
                        namedValue,
                    )

                CollectionViolation.ContainsNone(
                    message = message,
                    elements = elements,
                )
            }
        }

    fun <T, C : Collection<T>> contains(element: T): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.contains(element),
            ) {
                val message =
                    localization.getLocalization(
                        CollectionRuleType.Contains(
                            element = element,
                        ),
                        namedValue,
                    )

                CollectionViolation.Contains(
                    message = message,
                    element = element,
                )
            }
        }

    fun <T, C : Collection<T>> notContains(element: T): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                !namedValue.value.contains(element),
            ) {
                val message =
                    localization.getLocalization(
                        CollectionRuleType.NotContains(
                            element = element,
                        ),
                        namedValue,
                    )

                CollectionViolation.NotContains(
                    message = message,
                    element = element,
                )
            }
        }

    fun <T, C : Collection<T>> containsOnly(elements: Collection<T>): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.all { element -> element in elements },
            ) {
                val message =
                    localization.getLocalization(
                        CollectionRuleType.ContainsOnly(
                            elements = elements,
                        ),
                        namedValue,
                    )

                CollectionViolation.ContainsOnly(
                    message = message,
                    elements = elements,
                )
            }
        }

    fun <C : Collection<*>> notEmpty(): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.isNotEmpty(),
            ) {
                val message =
                    localization.getLocalization(
                        CollectionRuleType.NotEmpty,
                        namedValue,
                    )

                CollectionViolation.NotEmpty(
                    message = message,
                )
            }
        }

    fun <C : Collection<*>> distinct(): Rule<NamedValue<C>> =
        createNamedRule { namedValue ->
            validate(
                namedValue.value.size == namedValue.value.toSet().size,
            ) {
                val message =
                    localization.getLocalization(
                        CollectionRuleType.Distinct,
                        namedValue,
                    )

                CollectionViolation.Distinct(
                    message = message,
                )
            }
        }
}
