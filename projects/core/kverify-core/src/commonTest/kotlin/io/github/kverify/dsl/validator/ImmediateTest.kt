package io.github.kverify.dsl.validator

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.dsl.extension.asViolation
import io.github.kverify.dsl.extension.onInvalid
import io.github.kverify.dsl.extension.onValid
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.test.fail

class ImmediateTest :
    FunSpec({
        val message = "fail".asViolation()

        test("validateOrThrow") {
            shouldThrow<ValidationException> {
                validateOrThrow { onFailure(message) }
            }.violations.first() shouldBe message
        }

        test("validateFirst") {
            validateFirst {
                onFailure(message)
                fail("Code after first violation should not be executed")
            }.onValid {
                fail("Validation should fail")
            }.onInvalid { violations ->
                violations.size shouldBe 1
                violations.first() shouldBe message
            }
        }

        test("runValidatingFirst") {
            val expectedResult = "result"

            val failResult =
                runValidatingFirst {
                    onFailure(message)
                    fail("Code after first violation should not be executed")
                    expectedResult
                }

            failResult.isFailure shouldBe true
            shouldThrow<ValidationException> {
                failResult.getOrThrow()
            }.violations.first() shouldBe message

            val successResult =
                runValidatingFirst {
                    expectedResult
                }

            successResult.isSuccess shouldBe true
            successResult.getOrNull() shouldBe expectedResult
        }
    })
