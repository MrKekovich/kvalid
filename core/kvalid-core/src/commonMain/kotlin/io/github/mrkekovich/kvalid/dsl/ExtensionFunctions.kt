package io.github.mrkekovich.kvalid.dsl

import io.github.mrkekovich.kvalid.core.dto.NamedValue
import kotlin.reflect.KProperty

infix fun <T> T.withName(name: String): NamedValue<T> = NamedValue(name, this)

infix fun <T> String.withValue(value: T): NamedValue<T> = NamedValue(this, value)

inline fun <T> NamedValue<T>.nested(block: T.() -> Unit): NamedValue<T> {
    value.block()
    return this
}

/**
 * Extension function to convert a KProperty to a NamedValue.
 *
 * This function takes a value and creates a NamedValue instance
 * with the property's name and the provided value.
 *
 * @param T The type of the property's value.
 * @param value The value to be associated with the property.
 * @return A NamedValue instance containing the property's name and the provided value.
 */
fun <T> KProperty<T>.toNamed(value: T): NamedValue<T> = NamedValue(name, value)
