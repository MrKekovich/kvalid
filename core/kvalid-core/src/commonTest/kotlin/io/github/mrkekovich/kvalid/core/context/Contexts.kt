package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.model.ValidationRule
import io.github.mrkekovich.kvalid.core.exception.ValidationException
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal object Contexts {
    val successContext = object : KValidContext {
        override fun rule(message: String, predicate: () -> Boolean): ValidationRule =
            ValidationRule(message, predicate).also {
                assertTrue(it.validate(), "Rule failed: $message")
            }

        override fun violation(message: String) {
            throw AssertionError("Unexpected violation: $message")
        }

        override fun <T> T.validate(message: String, predicate: ValidationPredicate<T>): T {
            assertTrue(predicate(this), "Validation failed: $message")
            return this
        }
    }

    val failContext = object : KValidContext {
        override fun rule(message: String, predicate: () -> Boolean): ValidationRule =
            ValidationRule(message, predicate).also {
                assertFalse(it.validate(), "Rule unexpectedly passed: $message")
            }

        override fun violation(message: String) {
            throw ValidationException(message)
        }

        override fun <T> T.validate(message: String, predicate: ValidationPredicate<T>): T {
            assertFalse(predicate(this), "Validation unexpectedly passed: $message")
            return this
        }
    }
}
