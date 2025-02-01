package io.github.kverify.core.jvm.model

import io.github.kverify.core.jvm.exception.PropertyAccessException
import io.github.kverify.core.model.NamedValue
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.isAccessible

// JVM-specific code. For Kotlin Multiplatform, see `NamedValue` class

/**
 * Converts a [KProperty] to a [NamedValue].
 *
 * Uses reflection to retrieve the property value and creates a [NamedValue] instance
 * containing the property's name and the value.
 *
 * If the property value cannot be accessed, a [PropertyAccessException] is thrown
 * with a descriptive message suggesting the correct usage.
 *
 * @param T The type of the property's value.
 * @return A [NamedValue] containing the property name and the retrieved value.
 * @throws PropertyAccessException if the property value cannot be accessed.
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
