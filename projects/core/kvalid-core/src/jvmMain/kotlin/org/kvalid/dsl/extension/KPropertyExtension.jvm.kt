package org.kvalid.dsl.extension

import org.kvalid.core.model.NamedValue
import org.kvalid.dsl.exception.PropertyAccessException
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.isAccessible

/**
 * Extension function to convert a [KProperty] to a [NamedValue].
 *
 * This function attempts to get the value of the property using reflection
 * and creates a [NamedValue] instance with the property's and the retrieved value.
 *
 * If the property value cannot be accessed, a [PropertyAccessException] is thrown with
 * a helpful message guiding the correct usage.
 *
 * @param T The type of the property's value.
 * @return A NamedValue instance containing the property's name and the retrieved value.
 * @throws PropertyAccessException if the property value cannot be accessed.
 */
fun <T> KProperty<T>.toNamed(): NamedValue<T> =
    try {
        val name = name

        val original = getter.isAccessible

        getter.isAccessible = true
        val named = NamedValue(name, getter.call())
        getter.isAccessible = original

        named
    } catch (e: IllegalArgumentException) {
        throw PropertyAccessException(
            "Cannot access value of the property '$name' without an instance. Hint: Use `instance::prop` instead of `Class::prop`",
            e,
        )
    } catch (e: IllegalAccessException) {
        throw PropertyAccessException(
            "Illegal access to the property '$name'. Ensure the property is accessible and belongs to an instance.",
            e,
        )
    }
