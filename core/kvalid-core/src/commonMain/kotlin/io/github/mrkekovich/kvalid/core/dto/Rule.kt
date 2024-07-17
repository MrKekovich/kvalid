package io.github.mrkekovich.kvalid.core.dto

import io.github.mrkekovich.kvalid.core.context.ValidationPredicate

sealed interface Rule {
    val failMessage: String
    fun validate(): Boolean
}

data class ValidationRule(
    override val failMessage: String,
    val predicate: () -> Boolean
) : Rule {
    override fun validate(): Boolean = predicate()
}

data class LazyValidationRule<T>(
    override val failMessage: String,
    val value: T,
    val predicate: ValidationPredicate<T>
) : Rule {
    override fun validate(): Boolean = predicate(value)
}
