package io.github.mrkekovich.kvalid.dsl

import io.github.mrkekovich.kvalid.core.model.NamedValue
import io.github.mrkekovich.kvalid.dsl.exceptions.PropertyAccessException
import kotlin.reflect.KProperty

/**
 * Extension function to convert a KProperty to a NamedValue.
 *
 * This function attempts to get the value of the property using reflection
 * and creates a NamedValue instance with the property's name and the retrieved value.
 *
 * If the property value cannot be accessed, a PropertyAccessException is thrown with
 * a helpful message guiding the correct usage.
 *
 * @param T The type of the property's value.
 * @return A NamedValue instance containing the property's name and the retrieved value.
 * @throws PropertyAccessException if the property value cannot be accessed.
 */
fun <T> KProperty<T>.toNamed(): NamedValue<T> = try {
    NamedValue(name, getter.call())
} catch (e: IllegalArgumentException) {
    throw PropertyAccessException("Cannot access value of the property '$name' without an instance. Hint: Use `instance::prop` instead of Class::prop")
}
