package io.github.kverify.core.model

import kotlin.reflect.KProperty

data class NamedValue<T>(
    val name: String,
    val value: T,
)

infix fun <T> T.withName(name: String): NamedValue<T> = NamedValue(name, this)

infix fun <T> String.withValue(value: T): NamedValue<T> = NamedValue(this, value)

fun <T> KProperty<T>.toNamed(value: T): NamedValue<T> = NamedValue(name, value)

@Suppress("UNCHECKED_CAST")
inline infix fun <T> NamedValue<T?>.ifNotNull(block: (NamedValue<T>) -> Unit): NamedValue<T?> {
    if (value != null) block(this as NamedValue<T>)
    return this
}

@Suppress("UNCHECKED_CAST")
fun <T> NamedValue<T?>.unwrapOrNull(): NamedValue<T>? =
    if (value != null) {
        this as NamedValue<T>
    } else {
        null
    }
