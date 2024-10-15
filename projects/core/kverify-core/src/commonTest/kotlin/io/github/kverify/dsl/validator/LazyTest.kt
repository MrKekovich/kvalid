package io.github.kverify.dsl.validator

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class LazyTest :
    FunSpec({
        test("validateLazy") {
            var executionCount = 0

            val result =
                validateLazy {
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
