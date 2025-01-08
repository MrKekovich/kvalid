package io.github.kverify.dsl.validator

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.dsl.extension.onInvalid
import io.github.kverify.dsl.extension.onValid
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.test.fail

class AggregateTest :
    FunSpec({
        test("validateAll") {
            val message = "fail"
            validateAll {
                onFailure(message)
                onFailure(message)
            }.onValid {
                fail("should not be valid")
            }.onInvalid { violationMessages ->
                violationMessages.size shouldBe 2

                violationMessages.forEach {
                    it shouldBe message
                }
            }
        }

        test("runValidating") {
            val expectedResult = "result"
            val failMessage = "Fail"

            val failResult =
                runValidatingAll {
                    onFailure(failMessage)
                    expectedResult
                }

            failResult.isFailure shouldBe true
            shouldThrow<ValidationException> {
                failResult.getOrThrow()
            }.violationMessages.first() shouldBe failMessage

            val successResult =
                runValidatingAll {
                    expectedResult
                }

            successResult.isSuccess shouldBe true
            successResult.getOrNull() shouldBe expectedResult
        }
    })
