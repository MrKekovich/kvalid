package io.github.kverify.rule

private typealias ComparablePredicate<T> = Predicate<Comparable<T>>

internal object ComparableRules {
    fun <T : Comparable<T>> equalTo(other: T): ComparablePredicate<T> =
        {
            it == other
        }

    fun <T : Comparable<T>> notEqualTo(other: T): ComparablePredicate<T> =
        {
            it != other
        }

    fun <T : Comparable<T>> greaterThan(other: T): ComparablePredicate<T> =
        {
            it > other
        }

    fun <T : Comparable<T>> greaterThanOrEqualTo(other: T): ComparablePredicate<T> =
        {
            it >= other
        }

    fun <T : Comparable<T>> lessThan(other: T): ComparablePredicate<T> =
        {
            it < other
        }

    fun <T : Comparable<T>> lessThanOrEqualTo(other: T): ComparablePredicate<T> =
        {
            it <= other
        }

    fun <T : Comparable<T>> between(
        min: T,
        max: T,
        inclusive: Boolean = true,
    ): ComparablePredicate<T> =
        if (inclusive) {
            { it >= min && it <= max }
        } else {
            { it > min && it < max }
        }

    fun <T : Comparable<T>> notBetween(
        min: T,
        max: T,
        inclusive: Boolean = true,
    ): ComparablePredicate<T> =
        if (inclusive) {
            { it < min || it > max }
        } else {
            { it <= min || it >= max }
        }
}
