package io.github.kverify.dsl.extension

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.ValidationResult
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.test.DefaultAsserter.fail

class ValidationResultExtensionTest :
    FunSpec({
        val message = "message"

        val validResult = ValidationResult()
        val invalidResult = ValidationResult(message)

        test("throwOnFailure") {
            validResult.throwOnFailure()

            shouldThrow<ValidationException> {
                invalidResult.throwOnFailure()
            }.cause shouldBe null
        }

        test("onValid") {
            var check = false
            validResult.onValid { check = true }
            check shouldBe true

            invalidResult.onValid { fail("should not be called on invalid result") }
        }

        test("onInvalid") {
            var check = false
            invalidResult.onInvalid { check = true }
            check shouldBe true

            validResult.onInvalid { fail("should not be called on valid result") }
        }

        test("fold") {
            validResult.fold(
                onValid = { true },
                onInvalid = { fail("should not be called on valid result") },
            ) shouldBe true

            invalidResult.fold(
                onValid = { fail("should not be called on invalid result") },
                onInvalid = { true },
            ) shouldBe true
        }

        test("asExceptionOrNull") {
            validResult.asExceptionOrNull() shouldBe null
            invalidResult.asExceptionOrNull()?.violationMessages shouldBe listOf(message)
        }
    })
