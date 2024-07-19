package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.model.NamedValue
import io.github.mrkekovich.kvalid.core.model.Rule

/**
 * Callback function that returns a [Boolean].
 */
typealias Predicate = () -> Boolean

/**
 * Callback function that takes a value of type `T` and returns a [Boolean].
 * @param T the type of the value.
 */
typealias ValuePredicate<T> = (T) -> Boolean

/**
 * Callback function that takes a [NamedValue] of type `T` and returns a [String].
 * @param T the type of the value.
 */
typealias MessageCallback<T> = (NamedValue<T>) -> String

/**
 * Callback function that takes a value of type `T` and returns a [Rule].
 * @param T the type of the value.
 */
typealias RuleCallback<T> = (T) -> Rule

/**
 * Callback function that takes a [NamedValue] of type `T` and returns a [Rule].
 * @param T the type of the value.
 */
typealias NamedValueRuleCallback<T> = (NamedValue<T>) -> Rule


/**
 * Validation context. The core of all validation contexts.
 */
interface ValidationContext {
    /**
     * Validates based on a simple [predicate] and [message].
     *
     * @param message The failure message.
     * @param predicate The predicate to validate the value.
     */
    fun validate(
        message: String,
        predicate: Predicate,
    )

    /**
     * Validates using a rule.
     *
     * @param rule The rule to validate.
     */
    fun validate(
        rule: Rule,
    ) {
        validate(rule.failMessage) { rule.validate() }
    }

    /**
     * Validates the value against the specified [predicate].
     *
     * @param T the type of the value.
     * @param message the failure message.
     * @param predicate the [ValuePredicate] to validate the value.
     */
    fun <T> T.validate(
        message: String,
        predicate: ValuePredicate<T>,
    ): T {
        this@ValidationContext.validate(message) { predicate(this) }
        return this
    }

    /**
     * Validates the named value against the specified predicate, using a function to generate the failure message.
     *
     * ```
     * age.validate(
     *     message = { "${it.name} must be at least 18" },
     *     predicate = { it >= 18 }
     * )
     * ```
     *
     * @param T the type of the value.
     * @param message a [MessageCallback] that takes the [NamedValue] and returns the failure message.
     * @param predicate the [ValuePredicate] to validate the value.
     */
    fun <T> NamedValue<T>.validate(
        message: MessageCallback<T>,
        predicate: ValuePredicate<T>,
    ): NamedValue<T> {
        value.validate(message(this), predicate)
        return this
    }

    /**
     * Validates the value using a rule callback function to generate a rule dynamically.
     *
     * @param ruleCallback The [RuleCallback] function that returns a rule based on the value.
     */
    fun <T> T.validate(
        ruleCallback: RuleCallback<T>,
    ): T {
        validate(ruleCallback(this))
        return this
    }

    /**
     * Validates the underlying value of this NamedValue using the provided rule callback.
     *
     * @param ruleCallback The [RuleCallback] function that generates a validation rule based on the value.
     * @return This [NamedValue] instance after validation.
     */
    fun <T> NamedValue<T>.validateValue(
        ruleCallback: RuleCallback<T>,
    ): NamedValue<T> {
        validate(ruleCallback(value))
        return this
    }

    /**
     * Validates the value using a rule callback function to generate a rule dynamically with a message.
     *
     * @param ruleCallbackWithMessage The [NamedValueRuleCallback] function that returns a rule based on the [NamedValue].
     */
    fun <T> NamedValue<T>.validate(
        ruleCallbackWithMessage: NamedValueRuleCallback<T>,
    ): NamedValue<T> {
        validate(ruleCallbackWithMessage(this))
        return this
    }
}
