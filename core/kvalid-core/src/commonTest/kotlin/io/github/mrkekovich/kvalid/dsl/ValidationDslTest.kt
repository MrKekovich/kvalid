package io.github.mrkekovich.kvalid.dsl

import io.github.mrkekovich.kvalid.core.model.ValidationResult
import io.github.mrkekovich.kvalid.core.model.errorsOrEmpty
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import io.github.mrkekovich.kvalid.core.exception.ValidationException

class ValidationDslTest : FunSpec({

    test("validateAll should aggregate all violations") {
        val result = validateAll {
            rule("First rule") { false }
            rule("Second rule") { false }
        }
        result.errorsOrEmpty().size shouldBe 2
    }

    test("validateWithFailFast should return only the first violation") {
        val result = validateWithFailFast {
            rule("First rule") { false }
            rule("Second rule") { false }
        }
        result.errorsOrEmpty().size shouldBe 1
    }

    test("validateAll should return valid result when all rules pass") {
        val result = validateAll {
            rule("First rule") { true }
        }
        result shouldBe ValidationResult.valid()
    }

    test("validateWithFailFast should return valid result when all rules pass") {
        val result = validateWithFailFast {
            rule("First rule") { true }
        }
        result shouldBe ValidationResult.valid()
    }

    test("validateOrThrow should not throw when all rules pass") {
        validateOrThrow {
            rule("First rule") { true }
        }
    }

    test("validateOrThrow should throw ValidationException when a rule fails") {
        try {
            validateOrThrow {
                rule("First rule") { false }
            }
        } catch (e: ValidationException) {
            e.message shouldBe "First rule"
        }
    }

    test("validateLazily should return a sequence of ValidationExceptions") {
        val result = validateLazily {
            rule("First rule") { false }
            rule("Second rule") { false }
        }.toList()

        result.size shouldBe 2
        result[0].message shouldBe "First rule"
        result[1].message shouldBe "Second rule"
    }

    test("withName extension function") {
        checkAll(
            Arb.string(),
            Arb.int()
        ) { name, value ->
            val namedValue = value withName name
            namedValue.name shouldBe name
            namedValue.value shouldBe value
        }
    }

    test("validateLazily should not execute validation rules until accessed") {
        var executionCount = 0

        val lazyValidation = validateLazily {
            rule("First rule") {
                executionCount++
                false
            }
            rule("Second rule") {
                executionCount++
                true
            }
            rule("Third rule") {
                executionCount++
                false
            }
        }

        executionCount shouldBe 0

        val firstViolation = lazyValidation.firstOrNull()

        executionCount shouldBe 1
        firstViolation?.message shouldBe "First rule"

        val allViolations = lazyValidation.toList()

        executionCount shouldBe 4 // first rule was executed twice
        allViolations.size shouldBe 2
        allViolations[0].message shouldBe "First rule"
        allViolations[1].message shouldBe "Third rule"
    }
})