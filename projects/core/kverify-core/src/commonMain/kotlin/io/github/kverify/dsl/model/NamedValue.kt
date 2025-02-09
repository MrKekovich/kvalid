package io.github.kverify.dsl.model

import io.github.kverify.core.model.NamedValue

/**
 * Associates `this` value with a given name and returns it as a [NamedValue].
 *
 * @param T The type of the value.
 * @param name The name to associate with this value.
 * @return A [NamedValue] containing the provided name and `this` value.
 */
infix fun <T> T.withName(name: String): NamedValue<T> = NamedValue(name, this)

/**
 * Associates `this` name (String) with a given value and returns it as a [NamedValue].
 *
 * @param T The type of the value.
 * @param value The value to associate with this name.
 * @return A [NamedValue] containing `this` name and the provided value.
 */
infix fun <T> String.withValue(value: T): NamedValue<T> = NamedValue(this, value)

/**
 * Executes the given block of code using the [NamedValue.value] as the parameter.
 *
 * This allows for nested operations on the value while maintaining the original [NamedValue].
 *
 * @param T The type of the value.
 * @param block The block of code to execute with the `value` as the parameter.
 * @return The original [NamedValue].
 */
inline fun <T> NamedValue<T>.nested(block: (T) -> Unit): NamedValue<T> {
    block(value)
    return this
}

/**
 * Executes the given block of code using the [NamedValue.value] as the parameter if the [NamedValue.value] is not null.
 *
 * This allows for nested operations on the value while maintaining the original [NamedValue].
 *
 * @param T The type of the value.
 * @param block The block of code to execute with the non-null `value` as the parameter.
 * @return The original [NamedValue].
 */
@Suppress("UNCHECKED_CAST")
inline infix fun <T> NamedValue<T?>.ifNotNull(block: (NamedValue<T>) -> Unit): NamedValue<T?> {
    if (value != null) block(this as NamedValue<T>)
    return this
}

/**
 * Returns the [NamedValue] if the value is **not** `null`, or `null` otherwise.
 *
 * This function acts as a utility to safely cast a nullable [NamedValue] to a non-nullable one
 * if the value is non-null, or return `null` if the value is `null`.
 *
 * @param T The type of the value.
 * @return The original [NamedValue] if the value is non-null, otherwise `null`.
 */
@Suppress("UNCHECKED_CAST")
fun <T> NamedValue<T?>.unwrapOrNull(): NamedValue<T>? =
    if (value != null) {
        this as NamedValue<T>
    } else {
        null
    }
