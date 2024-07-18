package io.github.mrkekovich.kvalid.dsl.model

import io.github.mrkekovich.kvalid.core.context.MessageCallback
import io.github.mrkekovich.kvalid.core.context.NamedValueRuleCallback
import io.github.mrkekovich.kvalid.core.context.RuleCallback
import io.github.mrkekovich.kvalid.core.context.ValuePredicate
import io.github.mrkekovich.kvalid.core.model.Rule

/**
 * Creates a new validation rule with the given message and predicate.
 *
 * @param message The failure message if the rule's predicate fails.
 * @param predicate The predicate function to validate against.
 * @return The created Rule instance.
 */
fun createRule(message: String, predicate: () -> Boolean): Rule = Rule(message, predicate)

/**
 * Creates a new validation rule callback with the given message and predicate.
 *
 * @param message The failure message if the rule's predicate fails.
 * @param predicate The validation predicate function to validate against.
 * @return A callback function that creates a Rule instance based on the provided value.
 */
fun <T> createRule(message: String, predicate: ValuePredicate<T>): RuleCallback<T> = {
    Rule(message) { predicate(it) }
}

/**
 * Creates a new validation rule callback using a message callback and a predicate.
 *
 * @param message The callback function that generates the failure message based on the NamedValue.
 * @param predicate The predicate function to validate against the value within NamedValue.
 * @return A function that takes a NamedValue and returns a Rule instance based on the provided message and predicate.
 */
fun <T> createRule(message: MessageCallback<T>, predicate: ValuePredicate<T>): NamedValueRuleCallback<T> = {
    Rule(message(it)) { predicate(it.value) }
}
