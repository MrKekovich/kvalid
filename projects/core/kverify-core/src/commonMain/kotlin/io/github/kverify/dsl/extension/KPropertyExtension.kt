package io.github.kverify.dsl.extension

import io.github.kverify.core.model.NamedValue
import kotlin.reflect.KProperty

/**
 * Converts a [KProperty] into a [NamedValue] by associating it with the specified [value].
 *
 * This extension function creates a [NamedValue] using the property's [KProperty.name]
 * and the provided [value].
 *
 * @param T The type of the property's value
 * @param value The value to associate with the property
 * @return A [NamedValue] instance containing the property's name and the specified [value]
 */
fun <T> KProperty<T>.toNamed(value: T): NamedValue<T> = NamedValue(name, value)
