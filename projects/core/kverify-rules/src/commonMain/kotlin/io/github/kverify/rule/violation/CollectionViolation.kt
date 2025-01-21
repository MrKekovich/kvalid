package io.github.kverify.rule.violation

import io.github.kverify.core.violation.Violation

sealed interface CollectionViolation : Violation {
    data class OfSize(
        override val message: String,
        val size: Int,
    ) : CollectionViolation

    data class NotOfSize(
        override val message: String,
        val size: Int,
    ) : CollectionViolation

    data class SizeBetween(
        override val message: String,
        val range: IntRange,
    ) : CollectionViolation

    data class SizeNotBetween(
        override val message: String,
        val range: IntRange,
    ) : CollectionViolation

    data class ContainsAll<T>(
        override val message: String,
        val elements: Collection<T>,
    ) : CollectionViolation

    data class ContainsNone<T>(
        override val message: String,
        val elements: Collection<T>,
    ) : CollectionViolation

    data class Contains<T>(
        override val message: String,
        val element: T,
    ) : CollectionViolation

    data class NotContains<T>(
        override val message: String,
        val element: T,
    ) : CollectionViolation

    data class ContainsOnly<T>(
        override val message: String,
        val elements: Collection<T>,
    ) : CollectionViolation

    data class NotEmpty(
        override val message: String,
    ) : CollectionViolation

    data class Distinct(
        override val message: String,
    ) : CollectionViolation
}
