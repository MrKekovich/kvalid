package io.github.kverify.dsl.model

import io.github.kverify.core.model.NamedValue

/**
 * Associates the receiver with the given [name] and returns a [NamedValue].
 *
 * @param T The type of the value
 * @param name The name to associate with the receiver
 * @return A [NamedValue] containing the provided [name] and the receiver as its value
 */
infix fun <T> T.withName(name: String): NamedValue<T> = NamedValue(name, this)

/**
 * Associates the receiver [String] with the given [value] and returns a [NamedValue].
 *
 * @param T The type of the [value]
 * @param value The value to associate with the receiver [String]
 * @return A [NamedValue] containing the receiver [String] as the name
 * and the provided [value] as its value
 */
infix fun <T> String.withValue(value: T): NamedValue<T> = NamedValue(this, value)

/**
 * Executes the given [block] using the [NamedValue.value] as the parameter.
 *
 * @param T The type of the [NamedValue]
 * @param block The lambda to execute with the [NamedValue.value]
 * @return The original [NamedValue] unchanged
 */
inline fun <T> NamedValue<T>.useValue(block: (T) -> Unit): NamedValue<T> {
    block(value)
    return this
}

/**
 * Executes the given [block] using the [NamedValue.value] as the parameter
 * if the value is not null.
 *
 * @param T The type of the value
 * @param block The lambda to execute with the non-null [NamedValue.value]
 * @return The original [NamedValue] unchanged
 */
@Suppress("UNCHECKED_CAST")
inline infix fun <T> NamedValue<T?>.ifNotNull(block: (NamedValue<T>) -> Unit): NamedValue<T?> {
    if (value != null) block(this as NamedValue<T>)
    return this
}

/**
 * Returns the original [NamedValue] if the value is not null, otherwise `null`.
 *
 * @param T The type of the [NamedValue]
 * @return The [NamedValue] with a non-null value or `null` if the value is null
 */
@Suppress("UNCHECKED_CAST")
fun <T> NamedValue<T?>.unwrapOrNull(): NamedValue<T>? = if (value != null) this as NamedValue<T> else null
