package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.model.NamedValue

typealias NamedComparable<T> = NamedValue<out Comparable<T>>

/**
 * Named comparable validation context.
 */
interface NamedComparableValidationContext : ValidationContext {

    /**
     * Validates that the comparable value is equal to the specified value.
     *
     * ```
     * age.withName("age").equalTo(30)
     * ```
     *
     * @param T the type of the value
     * @param other the value to compare against
     */
    fun <T> NamedComparable<T>.equalTo(
        other: T,
        message: String = "$name must be equal to $value",
    ): NamedComparable<T> {
        value.validate("must be equal to $value") { it == other }
        return this
    }

    /**
     * Validates that the comparable value is not equal to the specified value.
     *
     * ```
     * age.withName("age").notEqualTo(30)
     * ```
     *
     * @param T the type of the value
     * @param other the value to compare against
     */
    fun <T> NamedComparable<T>.notEqualTo(
        other: T,
        message: String = "$name must not be equal to $value",
    ): NamedComparable<T> {
        value.validate("must not be equal to $value") { it != other }
        return this
    }

    /**
     * Validates that the comparable value is greater than the specified minimum.
     *
     * ```
     * age.withName("age").greaterThan(18)
     * ```
     *
     * @param T the type of the value
     * @param min the minimum value
     */
    fun <T> NamedComparable<T>.greaterThan(
        min: T,
        message: String = "$name must be greater than $min",
    ): NamedComparable<T> {
        value.validate("must be greater than $min") { it > min }
        return this
    }

    /**
     * Validates that the comparable value is greater than or equal to the specified minimum.
     *
     * ```
     * age.withName("age").greaterThanOrEqualTo(18)
     * ```
     *
     * @param T the type of the value
     * @param min the minimum value
     */
    fun <T> NamedComparable<T>.greaterThanOrEqualTo(
        min: T,
        message: String = "$name must be greater than or equal to $min",
    ): NamedComparable<T> {
        value.validate("must be greater than or equal to $min") { it >= min }
        return this
    }

    /**
     * Validates that the comparable value is less than the specified maximum.
     *
     * ```
     * age.withName("age").lessThan(65)
     * ```
     *
     * @param T the type of the value
     * @param max the maximum value
     */
    fun <T> NamedComparable<T>.lessThan(
        max: T,
        message: String = "$name must be less than $max",
    ): NamedComparable<T> {
        value.validate("must be less than $max") { it < max }
        return this
    }

    /**
     * Validates that the comparable value is less than or equal to the specified maximum.
     *
     * ```
     * age.withName("age").lessThanOrEqualTo(65)
     * ```
     *
     * @param T the type of the value
     * @param max the maximum value
     */
    fun <T> NamedComparable<T>.lessThanOrEqualTo(
        max: T,
        message: String = "$name must be less than or equal to $max",
    ): NamedComparable<T> {
        value.validate("must be less than or equal to $max") { it <= max }
        return this
    }
}
