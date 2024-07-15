package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.dto.NamedValue

typealias NamedCollection<T> = NamedValue<Collection<T>>

/**
 * Collection validation context.
 */
interface NamedCollectionValidationContext : ValidationContext {
    /**
     * Validates that the collection has the specified size.
     *
     * ```
     * collection.named("myCollection").ofSize(5)
     * ```
     *
     * @param T the type of elements in the collection
     * @param size the required size of the collection
     * @param message the failure message if validation fails
     */
    fun <T> NamedCollection<T>.ofSize(
        size: Int,
        message: String = "$name must have size $size",
    ): NamedCollection<T> {
        value.validate(message) { it.size == size }
        return this
    }

    /**
     * Validates that the collection size is within the specified range.
     *
     * ```
     * collection.named("myCollection").ofSize(5..10)
     * ```
     *
     * @param T the type of elements in the collection
     * @param range the allowed size range of the collection
     * @param message the failure message if validation fails
     */
    fun <T> NamedCollection<T>.ofSize(
        range: IntRange,
        message: String = "$name must have size in range $range",
    ): NamedCollection<T> {
        value.validate(message) { it.size in range }
        return this
    }

    /**
     * Validates that the collection does not have the specified size.
     *
     * ```
     * collection.named("myCollection").notOfSize(5)
     * ```
     *
     * @param T the type of elements in the collection
     * @param size the prohibited size of the collection
     * @param message the failure message if validation fails
     */
    fun <T> NamedCollection<T>.notOfSize(
        size: Int,
        message: String = "$name must not have size $size",
    ): NamedCollection<T> {
        value.validate(message) { it.size != size }
        return this
    }

    /**
     * Validates that the collection size is not within the specified range.
     *
     * ```
     * collection.named("myCollection").notOfSize(5..10)
     * ```
     *
     * @param T the type of elements in the collection
     * @param range the prohibited size range of the collection
     * @param message the failure message if validation fails
     */
    fun <T> NamedCollection<T>.notOfSize(
        range: IntRange,
        message: String = "$name must not have size $range",
    ): NamedCollection<T> {
        value.validate(message) { it.size !in range }
        return this
    }

    /**
     * Validates that the collection has at least the specified number of elements.
     *
     * ```
     * collection.named("myCollection").minSize(5)
     * ```
     *
     * @param T the type of elements in the collection
     * @param min the minimum number of elements
     * @param message the failure message if validation fails
     */
    fun <T> NamedCollection<T>.minSize(
        min: Int,
        message: String = "$name must have at least $min elements",
    ): NamedCollection<T> {
        value.validate(message) { it.size >= min }
        return this
    }

    /**
     * Validates that the collection has at most the specified number of elements.
     *
     * ```
     * collection.named("myCollection").maxSize(5)
     * ```
     *
     * @param T the type of elements in the collection
     * @param max the maximum number of elements
     * @param message the failure message if validation fails
     */
    fun <T> NamedCollection<T>.maxSize(
        max: Int,
        message: String = "$name must have at most $max elements",
    ): NamedCollection<T> {
        value.validate(message) { it.size <= max }
        return this
    }

    /**
     * Validates that the collection contains the specified element.
     *
     * ```
     * collection.named("myCollection").contains("foo")
     * ```
     *
     * @param T the type of elements in the collection
     * @param element the element to check for
     * @param message the failure message if validation fails
     */
    fun <T> NamedCollection<T>.contains(
        element: T,
        message: String = "$name must contain $element",
    ): NamedCollection<T> {
        value.validate(message) { it.contains(element) }
        return this
    }

    /**
     * Validates that the collection does not contain the specified element.
     *
     * ```
     * collection.named("myCollection").notContains("foo")
     * ```
     *
     * @param T the type of elements in the collection
     * @param element the element to check for
     * @param message the failure message if validation fails
     */
    fun <T> NamedCollection<T>.notContains(
        element: T,
        message: String = "$name must not contain $element",
    ): NamedCollection<T> {
        value.validate(message) { !it.contains(element) }
        return this
    }

    /**
     * Validates that the collection contains all the specified elements.
     *
     * ```
     * collection.named("myCollection").containsAllOf(listOf("foo", "bar"))
     * ```
     *
     * @param T the type of elements in the collection
     * @param elements the elements to check for
     * @param message the failure message if validation fails
     */
    fun <T> NamedCollection<T>.containsAllOf(
        elements: Collection<T>,
        message: String = "$name must contain all of the following: [${elements.joinToString()}]",
    ): NamedCollection<T> {
        value.validate(message) { it.containsAll(elements) }
        return this
    }

    /**
     * Validates that the collection does not contain any of the specified elements.
     *
     * ```
     * collection.named("myCollection").containsNoneOf(listOf("foo", "bar"))
     * ```
     *
     * @param T the type of elements in the collection
     * @param elements the elements to check for
     * @param message the failure message if validation fails
     */
    fun <T> NamedCollection<T>.containsNoneOf(
        elements: Collection<T>,
        message: String = "$name must not contain all of the following: [${elements.joinToString()}]",
    ): NamedCollection<T> {
        value.validate(message) { !it.containsAll(elements) }
        return this
    }
}
