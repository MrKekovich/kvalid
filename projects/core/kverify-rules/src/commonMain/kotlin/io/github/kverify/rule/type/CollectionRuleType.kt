package io.github.kverify.rule.type

sealed interface CollectionRuleType : RuleType {
    data class OfSize(
        val size: Int,
    ) : CollectionRuleType

    data class NotOfSize(
        val size: Int,
    ) : CollectionRuleType

    data class SizeBetween(
        val range: IntRange,
    ) : CollectionRuleType {
        constructor(min: Int, max: Int) : this(min..max)
    }

    data class SizeNotBetween(
        val range: IntRange,
    ) : CollectionRuleType {
        constructor(min: Int, max: Int) : this(min..max)
    }

    data class ContainsAll<T>(
        val elements: Collection<T>,
    ) : CollectionRuleType

    data class ContainsNone<T>(
        val elements: Collection<T>,
    ) : CollectionRuleType

    data class Contains<T>(
        val element: T,
    ) : CollectionRuleType

    data class NotContains<T>(
        val element: T,
    ) : CollectionRuleType

    data class ContainsOnly<T>(
        val elements: Collection<T>,
    ) : CollectionRuleType

    data object NotEmpty : CollectionRuleType

    data object Distinct : CollectionRuleType
}
