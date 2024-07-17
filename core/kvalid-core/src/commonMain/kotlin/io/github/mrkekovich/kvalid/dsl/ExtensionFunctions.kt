package io.github.mrkekovich.kvalid.dsl

import io.github.mrkekovich.kvalid.core.dto.NamedValue
import io.github.mrkekovich.kvalid.core.dto.ValidationResult
import io.github.mrkekovich.kvalid.core.exception.ValidationException
import kotlin.reflect.KProperty

/**
 * Associates this value with a given name and returns it as a [NamedValue].
 *
 * @param T The type of the value.
 * @param name The name to associate with this value.
 * @return A [NamedValue] containing the provided name and this value.
 */
infix fun <T> T.withName(name: String): NamedValue<T> = NamedValue(name, this)

/**
 * Associates this name (String) with a given value and returns it as a [NamedValue].
 *
 * @param T The type of the value.
 * @param value The value to associate with this name.
 * @return A [NamedValue] containing this name and the provided value.
 */
infix fun <T> String.withValue(value: T): NamedValue<T> = NamedValue(this, value)

/**
 * Executes the given block of code using the [NamedValue.value] as the receiver and returns the [NamedValue].
 *
 * This allows for nested operations on the value while maintaining the original [NamedValue].
 *
 * @param T The type of the value.
 * @param block The block of code to execute with the value as the receiver.
 * @return The unchanged [NamedValue].
 */
inline fun <T> NamedValue<T>.nested(block: T.() -> Unit): NamedValue<T> {
    value.block()
    return this
}

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

/**
 * Converts a sequence of [ValidationException] instances to a [ValidationResult].
 *
 * If the sequence is empty, the result is valid. Otherwise, the result is invalid and contains the list of violations.
 *
 * @return A [ValidationResult] representing the outcome of the validation.
 */
fun Sequence<ValidationException>.toValidationResult(): ValidationResult {
    val violations = this.toList()

    return when {
        violations.isEmpty() -> ValidationResult.valid()
        else -> ValidationResult.invalid(violations)
    }
}
