package io.github.mrkekovich.kvalid.dsl.validator

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class LazyTest : FunSpec({
    test("validateLazily") {
        var executionCount = 0

        val result = validateLazily {
            rule("fail") {
                executionCount++
                false
            }

            rule("fail") {
                executionCount++
                false
            }

            rule("success") {
                executionCount++
                true
            }
        }

        executionCount shouldBe 0

        result.forEachIndexed { idx, exception ->
            exception.message shouldBe "fail"
            executionCount shouldBe idx + 1
        }

        executionCount shouldBe 3
    }
})