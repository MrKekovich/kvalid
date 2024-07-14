package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.dto.NamedValue

typealias ValidationPredicate<T> = (T) -> Boolean

interface ValidationContext {

    fun <T> NamedValue<T>.validate(
        message: String,
        predicate: ValidationPredicate<T>
    ): NamedValue<T>


    fun <T> NamedValue<T>.validateIf(
        message: String = "$name is invalid",
        condition: Boolean,
        predicate: ValidationPredicate<T>
    ): NamedValue<T> {
        if (condition) validate(message, predicate)
        return this
    }
}
