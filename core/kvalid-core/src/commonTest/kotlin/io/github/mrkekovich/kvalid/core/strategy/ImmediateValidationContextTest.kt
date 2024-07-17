package io.github.mrkekovich.kvalid.core.strategy

import io.github.mrkekovich.kvalid.core.exception.ValidationException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ImmediateValidationContextTest : FunSpec({
    val context = ImmediateValidationContext()

    test("validate should not throw exception for valid input") {
        context.run {
            5.validate("Value should be positive") { it > 0 }
        }
    }

    test("validate should throw exception for invalid input") {
        shouldThrow<ValidationException> {
            context.run {
                (-5).validate("Value should be positive") { it > 0 }
            }
        }.message shouldBe "Value should be positive"
    }

    test("rule should not throw exception for valid input") {
        context.run {
            rule("Value should be positive") { 5 > 0 }
        }
    }

    test("rule should throw exception for invalid input") {
        shouldThrow<ValidationException> {
            context.run {
                rule("Value should be positive") { -5 > 0 }
            }
        }.message shouldBe "Value should be positive"
    }

    test("violation should throw exception") {
        shouldThrow<ValidationException> {
            context.run {
                violation("This is a violation")
            }
        }.message shouldBe "This is a violation"
    }
})