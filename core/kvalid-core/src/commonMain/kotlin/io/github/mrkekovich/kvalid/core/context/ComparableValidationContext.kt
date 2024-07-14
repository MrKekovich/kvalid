package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.dto.NamedValue

typealias NamedComparable<T> = NamedValue<Comparable<T>>

/**
 * Validation context interface for validating named comparable values.
 */
interface ComparableValidationContext : ValidationContext {

    /**
     * Validates that the comparable value is equal to the specified value.
     *
     * ```
     * age.named("age").equalTo(30)
     * ```
     *
     * @param T the type of the value
     * @param value the value to compare against
     */
    fun <T> NamedComparable<T>.equalTo(value: T): NamedComparable<T> =
        validate("must be equal to $value") { it == value }

    /**
     * Validates that the comparable value is not equal to the specified value.
     *
     * ```
     * age.named("age").notEqualTo(30)
     * ```
     *
     * @param T the type of the value
     * @param value the value to compare against
     */
    fun <T> NamedComparable<T>.notEqualTo(value: T): NamedComparable<T> =
        validate("must not be equal to $value") { it != value }

    /**
     * Validates that the comparable value is greater than the specified minimum.
     *
     * ```
     * age.named("age").greaterThan(18)
     * ```
     *
     * @param T the type of the value
     * @param min the minimum value
     */
    fun <T> NamedComparable<T>.greaterThan(min: T): NamedComparable<T> =
        validate("must be greater than $min") { it > min }

    /**
     * Validates that the comparable value is greater than or equal to the specified minimum.
     *
     * ```
     * age.named("age").greaterThanOrEqualTo(18)
     * ```
     *
     * @param T the type of the value
     * @param min the minimum value
     */
    fun <T> NamedComparable<T>.greaterThanOrEqualTo(min: T): NamedComparable<T> =
        validate("must be greater than or equal to $min") { it >= min }

    /**
     * Validates that the comparable value is less than the specified maximum.
     *
     * ```
     * age.named("age").lessThan(65)
     * ```
     *
     * @param T the type of the value
     * @param max the maximum value
     */
    fun <T> NamedComparable<T>.lessThan(max: T): NamedComparable<T> =
        validate("must be less than $max") { it < max }

    /**
     * Validates that the comparable value is less than or equal to the specified maximum.
     *
     * ```
     * age.named("age").lessThanOrEqualTo(65)
     * ```
     *
     * @param T the type of the value
     * @param max the maximum value
     */
    fun <T> NamedComparable<T>.lessThanOrEqualTo(max: T): NamedComparable<T> =
        validate("must be less than or equal to $max") { it <= max }
}
