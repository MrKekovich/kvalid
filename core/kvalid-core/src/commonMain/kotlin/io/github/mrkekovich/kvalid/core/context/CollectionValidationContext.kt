package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.dto.NamedValue

typealias NamedCollection<T> = NamedValue<Collection<T>>

interface CollectionValidationContext : ValidationContext {
    fun <T> NamedCollection<T>.ofSize(
        size: Int,
        message: String = "$name must have size $size",
    ): NamedCollection<T> =
        validate(message) { it.size == size }

    fun <T> NamedCollection<T>.ofSize(
        range: IntRange,
        message: String = "$name must have size in range $range",
    ): NamedCollection<T> =
        validate(message) { it.size in range }

    fun <T> NamedCollection<T>.notOfSize(
        size: Int,
        message: String = "$name must not have size $size",
    ): NamedCollection<T> =
        validate(message) { it.size != size }

    fun <T> NamedCollection<T>.notOfSize(
        range: IntRange,
        message: String = "$name must not have size $range",
    ): NamedCollection<T> =
        validate(message) { it.size !in range }

    fun <T> NamedCollection<T>.minSize(
        min: Int,
        message: String = "$name must have at least $min elements",
    ): NamedCollection<T> =
        validate(message) { it.size >= min }

    fun <T> NamedCollection<T>.maxSize(
        max: Int,
        message: String = "$name must have at most $max elements",
    ): NamedCollection<T> =
        validate(message) { it.size <= max }

    fun <T> NamedCollection<T>.contains(
        element: T,
        message: String = "$name must contain $element",
    ): NamedCollection<T> =
        validate(message) { it.contains(element) }

    fun <T> NamedCollection<T>.notContains(
        element: T,
        message: String = "$name must not contain $element",
    ): NamedCollection<T> =
        validate(message) { !it.contains(element) }

    fun <T> NamedCollection<T>.containsAllOf(
        elements: Collection<T>,
        message: String = "$name must contain all of the following: [${elements.joinToString()}]",
    ): NamedCollection<T> =
        validate(message) { it.containsAll(elements) }

    fun <T> NamedCollection<T>.containsNoneOf(
        elements: Collection<T>,
        message: String = "$name must not contain all of the following: [${elements.joinToString()}]",
    ): NamedCollection<T> =
        validate(message) { !it.containsAll(elements) }
}
