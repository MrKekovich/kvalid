package io.github.kverify.rule

private typealias CollectionPredicate<T> = (Collection<T>) -> Boolean

object CollectionRules {
    fun ofSize(size: Int): CollectionPredicate<*> =
        {
            it.size == size
        }

    fun notOfSize(size: Int): CollectionPredicate<*> =
        {
            it.size != size
        }

    fun sizeBetween(range: IntRange): CollectionPredicate<*> =
        {
            it.size in range
        }

    fun sizeNotBetween(range: IntRange): CollectionPredicate<*> =
        {
            it.size !in range
        }

    fun sizeBetween(
        min: Int,
        max: Int,
    ): CollectionPredicate<*> =
        sizeBetween(
            min..max,
        )

    fun sizeNotBetween(
        min: Int,
        max: Int,
    ): CollectionPredicate<*> =
        sizeNotBetween(
            min..max,
        )

    fun <T> containsAll(elements: Collection<T>): CollectionPredicate<T> =
        { collection ->
            collection.containsAll(elements)
        }

    fun <T> containsNone(elements: Collection<T>): CollectionPredicate<T> =
        { collection ->
            elements.none { it in collection }
        }

    fun <T> contains(element: T): CollectionPredicate<T> =
        {
            it.contains(element)
        }

    fun <T> notContains(element: T): CollectionPredicate<T> =
        {
            !it.contains(element)
        }

    fun <T> containsOnly(allowedElements: Collection<T>): CollectionPredicate<T> =
        {
            it.all { element -> element in allowedElements }
        }

    val notEmpty: CollectionPredicate<*> = {
        it.isNotEmpty()
    }

    val distinct: CollectionPredicate<*> =
        {
            it.size == it.distinct().size
        }
}
