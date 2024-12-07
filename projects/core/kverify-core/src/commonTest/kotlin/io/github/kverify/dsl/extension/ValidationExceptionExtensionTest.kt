package io.github.kverify.dsl.extension

import io.github.kverify.core.exception.ValidationException
import io.kotest.assertions.shouldFail
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.test.DefaultAsserter.fail

class ValidationExceptionExtensionTest :
    FunSpec({
        test("throwIfPresent") {
            val message = "Error"
            shouldThrow<ValidationException> {
                ValidationException(message).throwIfPresent()
            }.message shouldBe message

            null.throwIfPresent()
        }

        test("onInvalid") {
            null.onInvalid { fail("Should not be called") }

            val message = "should be called"
            shouldFail {
                null.onValid { fail(message) }
            }.message shouldBe message
        }

        test("onValid") {
            ValidationException("")
                .onValid {
                    fail("Should not be called")
                }

            val message = "should be called"
            shouldFail {
                ValidationException("")
                    .onInvalid { fail(message) }
            }.message shouldBe message
        }

        test("fold") {
            null.fold<Unit>(
                onValid = { },
                onInvalid = {
                    fail("Should not be called")
                },
            )

            ValidationException("")
                .fold<Unit>(
                    onValid = {
                        fail("Should not be called")
                    },
                    onInvalid = { },
                )
        }
    })
