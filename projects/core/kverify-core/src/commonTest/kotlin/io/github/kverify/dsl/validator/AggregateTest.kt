package io.github.kverify.dsl.validator

import io.github.kverify.dsl.extension.onInvalid
import io.github.kverify.dsl.extension.onValid
import io.github.kverify.dsl.extension.violation
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.test.fail

class AggregateTest :
    FunSpec({
        test("validateAll") {
            val message = "fail"
            validateAll {
                violation(message)
                violation(message)
            }.onValid {
                fail("should not be valid")
            }.onInvalid { violationMessages ->
                violationMessages.size shouldBe 2

                violationMessages.forEach {
                    it shouldBe message
                }
            }
        }
    })
