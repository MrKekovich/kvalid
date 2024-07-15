package io.github.mrkekovich.kvalid.core

import io.github.mrkekovich.kvalid.core.dto.errorsOrEmpty
import io.github.mrkekovich.kvalid.core.dto.isInvalid
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
        assertEquals(4, result.errorsOrEmpty().size)
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
        assertEquals(1, result.errorsOrEmpty().size)
        assertEquals(2, executionCount, "FailFast should stop at the first violation")
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
        assertEquals(1, result.errorsOrEmpty().size)
    }

    @Test
    fun testCollectAllWithCustomViolation() {
        val result = collectViolations {
            "".named("empty").notBlank()
            violation("custom violation")
        }

        assertTrue(result.isInvalid)
        assertEquals(2, result.errorsOrEmpty().size)
    }
}