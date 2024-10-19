package io.github.kverify.dsl.validator

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class LazyTest :
    FunSpec({
        test("validateLazy") {
            var executionCount = 0
            val message = "fail"

            val result =
                validateLazily {
                    validate(message) {
                        executionCount++
                        false
                    }

                    validate(message) {
                        executionCount++
                        false
                    }

                    validate("should not fail") {
                        executionCount++
                        true
                    }
                }

            executionCount shouldBe 0

            result.forEachIndexed { index, validationException ->
                validationException.message shouldBe message
                executionCount shouldBe index + 1
            }

            executionCount shouldBe 3
        }
    })
