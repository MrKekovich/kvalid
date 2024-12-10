package io.github.kverify.rule

@Suppress("TooManyFunctions")
internal object CollectionRules {
    fun <C : Collection<*>> ofSize(size: Int): Predicate<C> =
        {
            it.size == size
        }

    fun <C : Collection<*>> notOfSize(size: Int): Predicate<C> =
        {
            it.size != size
        }

    fun <C : Collection<*>> sizeBetween(range: IntRange): Predicate<C> =
        {
            it.size in range
        }

    fun <C : Collection<*>> sizeNotBetween(range: IntRange): Predicate<C> =
        {
            it.size !in range
        }

    fun <C : Collection<*>> sizeBetween(
        min: Int,
        max: Int,
    ): Predicate<C> =
        sizeBetween(
            min..max,
        )

    fun <C : Collection<*>> sizeNotBetween(
        min: Int,
        max: Int,
    ): Predicate<C> =
        sizeNotBetween(
            min..max,
        )

    fun <T, C : Collection<T>> containsAll(elements: Collection<T>): Predicate<C> =
        { collection ->
            collection.containsAll(elements)
        }

    fun <T, C : Collection<T>> containsNone(elements: Collection<T>): Predicate<C> =
        { collection ->
            elements.none { it in collection }
        }

    fun <T, C : Collection<T>> contains(element: T): Predicate<C> =
        {
            it.contains(element)
        }

    fun <T, C : Collection<T>> notContains(element: T): Predicate<C> =
        {
            !it.contains(element)
        }

    fun <T, C : Collection<T>> containsOnly(allowedElements: Collection<T>): Predicate<C> =
        {
            it.all { element -> element in allowedElements }
        }

    fun <C : Collection<*>> notEmpty(): Predicate<C> =
        {
            it.isNotEmpty()
        }

    fun <C : Collection<*>> distinct(): Predicate<C> =
        {
            it.size == it.distinct().size
        }
}
