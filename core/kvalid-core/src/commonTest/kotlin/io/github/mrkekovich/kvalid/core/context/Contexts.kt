package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.exception.ValidationException
import io.github.mrkekovich.kvalid.core.model.Rule
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal object Contexts {
    val successContext = object : KValidContext {
        override fun rule(message: String, predicate: Predicate): Rule =
            Rule(message, predicate).also {
                assertTrue(it.validate(), "Rule failed: $message")
            }

        override fun rule(rule: Rule) {
            assertTrue(rule.validate(), "Rule failed: ${rule.failMessage}")
        }

        override fun violation(message: String) {
            throw AssertionError("Unexpected violation: $message")
        }

        override fun <T> T.validate(message: String, predicate: ValuePredicate<T>): T {
            assertTrue(predicate(this), "Validation failed: $message")
            return this
        }
    }

    val failContext = object : KValidContext {
        override fun rule(message: String, predicate: Predicate): Rule =
            Rule(message, predicate).also {
                assertFalse(it.validate(), "Rule unexpectedly passed: $message")
            }

        override fun rule(rule: Rule) {
            assertFalse(rule.validate(), "Rule unexpectedly passed: ${rule.failMessage}")
        }

        override fun violation(message: String) {
            throw ValidationException(message)
        }

        override fun <T> T.validate(message: String, predicate: ValuePredicate<T>): T {
            assertFalse(predicate(this), "Validation unexpectedly passed: $message")
            return this
        }
    }
}
