package io.github.mrkekovich.kvalid.dsl.model

import io.github.mrkekovich.kvalid.core.context.MessageCallback
import io.github.mrkekovich.kvalid.core.context.NamedValueRuleCallback
import io.github.mrkekovich.kvalid.core.context.Predicate
import io.github.mrkekovich.kvalid.core.context.RuleCallback
import io.github.mrkekovich.kvalid.core.context.ValuePredicate
import io.github.mrkekovich.kvalid.core.model.NamedValue
import io.github.mrkekovich.kvalid.core.model.Rule

/**
 * Creates a new validation rule with the given [message] and [predicate].
 *
 * @param message The failure message if the rule's predicate fails.
 * @param predicate The [Predicate] function to validate against.
 * @return The created [Rule] instance.
 */
fun createRule(message: String, predicate: Predicate): Rule = Rule(message, predicate)

/**
 * Creates a new [RuleCallback] with the given [message] and [ValuePredicate].
 *
 * **Example**:
 * ```
 * val rule = createRule<Int>("Value must be positive") { it > 0 }
 * throwOnFailure {
 *     1.validate(rule) // (1 > 0) == true // passes
 *     0.validate(rule) // (0 > 0) == false // fails
 * }
 *
 * ```
 *
 * @param message The failure message if the rule's predicate fails.
 * @param predicate The validation predicate function, that takes a value of type [T] to validate against.
 * @param T the type of the value, that should [predicate] take.
 * @return A callback function that creates a Rule instance based on the provided [T] value.
 */
fun <T> createRule(message: String, predicate: ValuePredicate<T>): RuleCallback<T> = {
    Rule(message) { predicate(it) }
}

/**
 * Creates a new [NamedValueRuleCallback] using a [MessageCallback] and a [ValuePredicate].
 *
 * **Example**:
 *
 * ```
 * val rule = createRule<String>(
 *     message = { "'${it.name}' must not be equal to 'test'. Value: '${it.value}'" },
 *     predicate = { it.value != "test" }
 * )
 * throwOnFailure {
 *     NamedValue("example", "notTest").validate(rule) // passes: "notTest" != "test"
 *     NamedValue("example", "test").validate(rule) // fails: "test" == "test"
 *     // message generated: "'example' must not be equal to 'test'. Value: 'notTest'"
 * }
 * ```
 *
 * @param message The [MessageCallback] function that generates the failure message based on the [NamedValue].
 * @param predicate The [ValuePredicate] function to validate against the [NamedValue.value].
 * @param T the type of the value, that should both predicates take.
 * @return A [NamedValueRuleCallback] that takes a [NamedValue] and returns a [Rule] instance based on the provided message and predicate.
 * @see NamedValue
 */
fun <T> createRule(message: MessageCallback<T>, predicate: ValuePredicate<T>): NamedValueRuleCallback<T> = {
    Rule(message(it)) { predicate(it.value) }
}
