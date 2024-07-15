package io.github.mrkekovich.kvalid.core

import io.github.mrkekovich.kvalid.core.context.NamedStringValidationContext
import io.github.mrkekovich.kvalid.core.context.ValidationPredicate
import io.github.mrkekovich.kvalid.dsl.named
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class ValidationTest {
    @Test
    fun namedString() {
        val successContext = object : NamedStringValidationContext {
            override fun <T> T.validate(message: String, predicate: ValidationPredicate<T>): T {
                assertTrue(predicate(this))
                return this
            }
        }
        val failContext = object : NamedStringValidationContext {
            override fun <T> T.validate(message: String, predicate: ValidationPredicate<T>): T {
                assertFalse(predicate(this))
                return this
            }
        }

        val string = "Hello"

        with(successContext) {
            string.named("greeting")
                .notBlank()
                .ofLength(string.length)
                .ofLength(0..string.length)
                .notOfLength(string.length - 1)
                .notOfLength(string.length + 1)
                .notOfLength((string.length + 1)..1000)
                .notOfLength(string.indices)
                .minLength(string.length)
                .minLength(string.length - 1)
                .maxLength(string.length)
                .maxLength(string.length + 1)
                .matches(Regex("^[A-Z][a-z]{4}$"))
                .matches("^[A-Z][a-z]{4}\$")
                .notMatches(Regex("^[a-z][A-Z]{4}$"))
                .notMatches("^[a-z][A-Z]{4}\$")
        }
    }
}