package io.github.kverify.rule.violation

import io.github.kverify.core.violation.Violation

sealed interface ComparableViolation : Violation {
    data class EqualTo<T : Comparable<T>>(
        override val message: String,
        val other: T,
    ) : ComparableViolation

    data class NotEqualTo<T : Comparable<T>>(
        override val message: String,
        val other: T,
    ) : ComparableViolation

    data class GreaterThan<T : Comparable<T>>(
        override val message: String,
        val other: T,
    ) : ComparableViolation

    data class GreaterThanOrEqualTo<T : Comparable<T>>(
        override val message: String,
        val other: T,
    ) : ComparableViolation

    data class LessThan<T : Comparable<T>>(
        override val message: String,
        val other: T,
    ) : ComparableViolation

    data class LessThanOrEqualTo<T : Comparable<T>>(
        override val message: String,
        val other: T,
    ) : ComparableViolation

    data class Between<T : Comparable<T>>(
        override val message: String,
        val lower: T,
        val upper: T,
    ) : ComparableViolation

    data class NotBetween<T : Comparable<T>>(
        override val message: String,
        val lower: T,
        val upper: T,
    ) : ComparableViolation
}
