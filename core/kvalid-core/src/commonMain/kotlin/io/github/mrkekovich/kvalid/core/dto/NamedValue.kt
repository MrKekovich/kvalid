package io.github.mrkekovich.kvalid.core.dto

data class NamedValue<T>(
    val name: String,
    val value: T,
)
