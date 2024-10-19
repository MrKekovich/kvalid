package io.github.kverify.core.validator

import io.github.kverify.core.context.Predicate
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.model.Rule

/**
 * An implementation of ValidationContext that lazily evaluates validation rules.
 */
open class LazyValidator : ValidationContext {
    /**
     * The internal list of validation rules.
     */
    private val _rules = mutableListOf<Pair<String, Predicate>>()

    /**
     * A read-only view of the validation rules.
     */
    val rules: List<Pair<String, Predicate>> get() = _rules.toList()

    /**
     * Adds a new [Rule] with the given [message] and [predicate] to the [rules].
     *
     * @param message The failure message.
     * @param predicate The predicate to validate the value.
     */
    override fun validate(
        message: String,
        predicate: Predicate,
    ) {
        _rules.add(message to predicate)
    }
}
