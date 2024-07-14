package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.dto.NamedValue

typealias NamedComparable<T> = NamedValue<Comparable<T>>

interface ComparableValidationContext : ValidationContext {
    fun <T> NamedComparable<T>.equalTo(value: T): NamedComparable<T> =
        validate("must be equal to $value") { it == value }

    fun <T> NamedComparable<T>.notEqualTo(value: T): NamedComparable<T> =
        validate("must not be equal to $value") { it != value }

    fun <T> NamedComparable<T>.greaterThan(min: T): NamedComparable<T> =
        validate("must be greater than $min") { it > min }

    fun <T> NamedComparable<T>.greaterThanOrEqualTo(min: T): NamedComparable<T> =
        validate("must be greater than or equal to $min") { it >= min }

    fun <T> NamedComparable<T>.lessThan(max: T): NamedComparable<T> =
        validate("must be less than $max") { it < max }

    fun <T> NamedComparable<T>.lessThanOrEqualTo(max: T): NamedComparable<T> =
        validate("must be less than or equal to $max") { it <= max }
}
