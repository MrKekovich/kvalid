package io.github.kverify.core.model

/**
 * Represents a named key-value pair
 *
 * This class is commonly used in validation scenarios or any context where
 * associating a [name] with a [value] is required
 *
 * @param T The type of the value.
 * @property name The name associated with the value
 * @property value The value associated with the name
 */
data class NamedValue<T>(
    val name: String,
    val value: T,
)
