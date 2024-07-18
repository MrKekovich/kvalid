package io.github.mrkekovich.kvalid.dsl.validator

import io.github.mrkekovich.kvalid.core.exception.ValidationException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.test.fail

class ImmediateTest : FunSpec({
    test("validateOrThrow") {
        val exception = shouldThrow<ValidationException> {
            validateOrThrow {
                violation("fail")
            }
        }

        exception.message shouldBe "fail"
    }

    test("validateWithFailFast") {
        val result = validateWithFailFast {
            rule("fail") { false }
            rule("should not be executed") { false }
        }
            .onValid { fail("Validation should fail") }

        result.violations.size shouldBe 1
        result.violations.forEach {
            it.message shouldBe "fail"
        }
    }
})