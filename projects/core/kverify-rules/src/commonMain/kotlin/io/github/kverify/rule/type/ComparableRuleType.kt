package io.github.kverify.rule.type

sealed interface ComparableRuleType : RuleType {
    data class EqualTo<T : Comparable<T>>(
        val other: T,
    ) : ComparableRuleType

    data class NotEqualTo<T : Comparable<T>>(
        val other: T,
    ) : ComparableRuleType

    data class GreaterThan<T : Comparable<T>>(
        val other: T,
    ) : ComparableRuleType

    data class GreaterThanOrEqualTo<T : Comparable<T>>(
        val other: T,
    ) : ComparableRuleType

    data class LessThan<T : Comparable<T>>(
        val other: T,
    ) : ComparableRuleType

    data class LessThanOrEqualTo<T : Comparable<T>>(
        val other: T,
    ) : ComparableRuleType

    data class Between<T : Comparable<T>>(
        val lower: T,
        val upper: T,
    ) : ComparableRuleType

    data class NotBetween<T : Comparable<T>>(
        val lower: T,
        val upper: T,
    ) : ComparableRuleType
}
