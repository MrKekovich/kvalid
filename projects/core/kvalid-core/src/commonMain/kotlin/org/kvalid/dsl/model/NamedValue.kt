package org.kvalid.dsl.model

import org.kvalid.core.model.NamedValue

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
 * Executes the given block of code using the [NamedValue.value] as the receiver.
 *
 * This allows for nested operations on the value while maintaining the original [NamedValue].
 *
 * @param T The type of the value.
 * @param block The block of code to execute with the value as the receiver.
 * @return The original [NamedValue].
 */
inline fun <T> NamedValue<T>.nested(block: T.() -> Unit): NamedValue<T> {
    value.block()
    return this
}
