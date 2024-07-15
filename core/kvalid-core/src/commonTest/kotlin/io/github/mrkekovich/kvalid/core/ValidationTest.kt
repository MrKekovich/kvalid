package io.github.mrkekovich.kvalid.core

import io.github.mrkekovich.kvalid.core.context.KValidContext
import io.github.mrkekovich.kvalid.core.context.ValidationPredicate
import io.github.mrkekovich.kvalid.core.dto.ValidationRule
import io.github.mrkekovich.kvalid.dsl.named
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class ValidationTest {
    private val successContext = object : KValidContext {
        override fun rule(message: String, predicate: () -> Boolean): ValidationRule<Unit> =
            ValidationRule(Unit, message) { predicate() }.also {
                assertTrue(it.validate())
            }

        override fun <T> T.validate(message: String, predicate: ValidationPredicate<T>): T {
            assertTrue(predicate(this))
            return this
        }

    }

    private val failContext = object : KValidContext {
        override fun rule(message: String, predicate: () -> Boolean): ValidationRule<Unit> =
            ValidationRule(Unit, message) { predicate() }.also {
                assertFalse(it.validate())
            }

        override fun <T> T.validate(message: String, predicate: ValidationPredicate<T>): T {
            assertFalse(predicate(this))
            return this
        }
    }

    @Test
    fun namedString() {
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

    @Test
    fun namedList() {
        val list = listOf(1, 2, 3, 4, 5)
        val unique = listOf(1, 2, 3, 4, 5, 6, 7, 8) - list

        with(successContext) {
            list.named("list")
                .ofSize(list.size)
                .ofSize(0..list.size)
                .notOfSize(list.size - 1)
                .notOfSize(list.size + 1)
                .notOfSize((list.size + 1)..1000)
                .notOfSize(list.indices)
                .minSize(list.size)
                .minSize(list.size - 1)
                .maxSize(list.size)
                .maxSize(list.size + 1)
                .contains(list[0])
                .notContains(unique[0])
                .containsAllOf(list)
                .containsNoneOf(unique)
        }
    }

    @Test
    fun namedComparable() {
        val comparable = 5

        with(successContext) {
            comparable.named("comparable")
                .equalTo(comparable)
                .greaterThan(3)
                .greaterThanOrEqualTo(5)
                .lessThan(6)
                .lessThanOrEqualTo(5)
        }
    }

    @Test
    fun customValidation() {
        val custom = object {
            val string = "Hello"
            val list = listOf(1, 2, 3, 4, 5)
            val comparable = 5
        }

        with(successContext) {
            custom.validate("greeting must be 5 characters long") { it.string.length == 5 }
            custom.validate("list have 5 elements") { it.list.size == 5 }
            custom.validate("comparable must be 5") { it.comparable == 5 }

            rule("Rule must be true ") { true }
        }
    }
}