package io.github.kverify.core.jvm.model

import io.github.kverify.core.jvm.exception.PropertyAccessException
import io.github.kverify.core.model.NamedValue
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.isAccessible

// JVM-specific code. For Kotlin Multiplatform, see `NamedValue` class

/**
 * Converts a property to a [NamedValue] containing its name and value.
 *
 * This function attempts to retrieve the property value using reflection. If there are issues
 * accessing the property (e.g., missing instance or illegal access), a [PropertyAccessException]
 * is thrown with a helpful error message.
 */
fun <T> KProperty<T>.toNamed(): NamedValue<T> =
    try {
        val name = name

        val original = getter.isAccessible

        getter.isAccessible = true

        val named =
            NamedValue(
                name,
                getter.call(),
            )

        getter.isAccessible = original

        named
    } catch (e: IllegalArgumentException) {
        throw PropertyAccessException(
            """
            Cannot access value of the property '$name' without an instance.
            Hint: Use `instance::prop` instead of `Class::prop`
            """.trimIndent(),
            e,
        )
    } catch (e: IllegalAccessException) {
        throw PropertyAccessException(
            "Illegal access to the property '$name'. Ensure the property is accessible and belongs to an instance.",
            e,
        )
    }
