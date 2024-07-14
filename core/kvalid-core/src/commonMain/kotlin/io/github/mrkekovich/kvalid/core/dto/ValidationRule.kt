package io.github.mrkekovich.kvalid.core.dto

data class ValidationRule<T>(
    val value: T,
    val message: String,
    val predicate: (T) -> Boolean,
) {
    fun validate(): Boolean = predicate(value)
}
