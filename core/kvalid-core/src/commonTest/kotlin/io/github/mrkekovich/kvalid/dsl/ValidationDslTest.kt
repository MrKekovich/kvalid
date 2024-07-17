package io.github.mrkekovich.kvalid.dsl

import io.github.mrkekovich.kvalid.core.dto.ValidationResult
import io.github.mrkekovich.kvalid.core.dto.errorsOrEmpty
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
        // If we reach this point, no exception was thrown
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

        // At this point, no rules should have been executed
        executionCount shouldBe 0

        // Access the first element of the sequence
        val firstViolation = lazyValidation.firstOrNull()

        // Only the first rule should have been executed
        executionCount shouldBe 1
        firstViolation?.message shouldBe "First rule"

        // Access all elements of the sequence
        val allViolations = lazyValidation.toList()

        // All rules should have been executed now
        executionCount shouldBe 3
        allViolations.size shouldBe 2
        allViolations[0].message shouldBe "First rule"
        allViolations[1].message shouldBe "Third rule"
    }
})