package io.github.mrkekovich.kvalid.dsl.extension

import io.github.mrkekovich.kvalid.core.model.NamedValue
import kotlin.jvm.JvmName
import kotlin.reflect.KProperty

/**
 * Creates a [NamedValue] from a KProperty and a provided value.
 *
 * This function takes a property and a value, and returns a [NamedValue] containing the property's name and the provided value.
 *
 * @param T The type of the property's value.
 * @param value The value to associate with the property.
 * @return A [NamedValue] containing the property's name and the provided value.
 */
fun <T> KProperty<T>.toNamed(value: T): NamedValue<T> = NamedValue(name, value)