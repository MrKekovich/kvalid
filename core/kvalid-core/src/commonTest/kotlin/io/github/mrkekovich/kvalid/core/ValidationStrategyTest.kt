package io.github.mrkekovich.kvalid.core

import io.github.mrkekovich.kvalid.core.dto.ValidationResult
import io.github.mrkekovich.kvalid.core.dto.isInvalid
import io.github.mrkekovich.kvalid.core.strategy.LazyContext
import io.github.mrkekovich.kvalid.dsl.collectViolations
import io.github.mrkekovich.kvalid.dsl.failFast
import io.github.mrkekovich.kvalid.dsl.named
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

internal class ValidationStrategyTest {

    @Test
    fun testCollectAllContext() {
        val result = collectViolations {
            "".named("empty").notBlank()
            (-1).named("negative").greaterThanOrEqualTo(0)
            listOf(1, 2, 3).named("list").ofSize(4)
        }

        assertTrue(result.isInvalid)
        assertEquals(3, (result as ValidationResult.Invalid).errors.size)
    }

    @Test
    fun testFailFastContext() {
        var executionCount = 0

        val result = failFast {
            executionCount++
            "".named("empty").notBlank()

            executionCount++
            (-1).named("negative").greaterThanOrEqualTo(0)

            executionCount++
            listOf(1, 2, 3).named("list").ofSize(4)
        }

        assertTrue(result.isInvalid)
        assertEquals(1, (result as ValidationResult.Invalid).errors.size)
        assertEquals(1, executionCount, "FailFast should stop at the first violation")
    }

    @Test
    fun testLazyContext() {
        val context = LazyContext()
        var executionCount = 0

        with(context) {
            "".named("empty").notBlank()
            (-1).named("negative").greaterThanOrEqualTo(0)
            listOf(1, 2, 3).named("list").ofSize(4)

            rule("Dummy") {
                executionCount++
                true
            }
        }


        assertEquals(0, executionCount, "Lazy validation should not execute predicates immediately")

        val violations = context.validate()
        assertEquals(3, violations.size)
        assertEquals(1, executionCount, "Lazy validation should execute predicates after validation")
    }

    @Test
    fun testOptimizedFailFast() {
        val result = failFast {
            "".named("empty").notBlank()
            (-1).named("negative").greaterThanOrEqualTo(0)
            "valid".named("valid").validate("This should not be executed") {
                fail("This operation should not be executed in FailFast mode after a violation")
            }
        }

        assertTrue(result.isInvalid)
        assertEquals(1, (result as ValidationResult.Invalid).errors.size)
    }

    @Test
    fun testOptimizedLazyValidation() {
        val context = LazyContext()
        var executionCount = 0

        with(context) {
            "".named("empty").notBlank()
            (-1).named("negative").greaterThanOrEqualTo(0)
            listOf(1, 2, 3).named("list").ofSize(4)

            rule("Custom rule") {
                executionCount++
                false
            }
        }

        assertEquals(0, executionCount, "Lazy validation should not execute predicates immediately")

        val violations = context.validate()
        assertEquals(4, violations.size)
        assertEquals(1, executionCount, "Custom rule should be executed only once during validation")
    }

    @Test
    fun testCollectAllWithCustomViolation() {
        val result = collectViolations {
            "".named("empty").notBlank()
            violation { "Custom violation" }
        }

        assertTrue(result.isInvalid)
        assertEquals(2, (result as ValidationResult.Invalid).errors.size)
    }
}