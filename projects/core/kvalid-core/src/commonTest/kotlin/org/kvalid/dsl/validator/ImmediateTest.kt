package org.kvalid.dsl.validator

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.kvalid.core.exception.ValidationException
import kotlin.test.fail

class ImmediateTest :
    FunSpec({
        test("throwOnFailure") {
            val exception =
                shouldThrow<ValidationException> {
                    throwOnFailure {
                        violation("fail")
                    }
                }

            exception.message shouldBe "fail"
        }

        test("validateFirst") {
            val result =
                validateFirst {
                    rule("fail") { false }
                    rule("should not be executed") { false }
                }.onValid { fail("Validation should fail") }

            result.violations.size shouldBe 1
            result.violations.forEach {
                it.message shouldBe "fail"
            }
        }
    })
