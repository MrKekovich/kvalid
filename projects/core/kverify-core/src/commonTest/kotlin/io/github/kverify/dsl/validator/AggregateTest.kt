package io.github.kverify.dsl.validator

import io.github.kverify.dsl.extension.violation
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.test.fail

class AggregateTest :
    FunSpec({
        test("validateAll") {
            val message = "fail"
            val result =
                validateAll {
                    violation(message)
                    violation(message)
                }.onValid { fail("should not be valid") }

            result.violations.size shouldBe 2

            result.violations.forEach {
                it.message shouldBe message
            }
        }
    })
