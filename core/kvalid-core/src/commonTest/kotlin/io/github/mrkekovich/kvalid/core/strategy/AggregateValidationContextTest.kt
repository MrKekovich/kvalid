package io.github.mrkekovich.kvalid.core.strategy

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class AggregateValidationContextTest : FunSpec({
    var context = AggregateValidationContext()

    afterTest {
        context = AggregateValidationContext()
    }

    test("validate should not add violation for valid input") {
        context.run {
            5.validate("Value should be positive") { it > 0 }
        }
        context.violations.isEmpty() shouldBe true
    }

    test("validate should add violation for invalid input") {
        context.run {
            (-5).validate("Value should be positive") { it > 0 }
        }
        context.violations.size shouldBe 1
        context.violations[0].message shouldBe "Value should be positive"
    }

    test("rule should not add violation for valid input") {
        context.run {
            rule("Value should be positive") { 5 > 0 }
        }
        context.violations.isEmpty() shouldBe true
    }

    test("rule should add violation for invalid input") {
        context.run {
            rule("Value should be positive") { -5 > 0 }
        }
        context.violations.size shouldBe 1
        context.violations[0].message shouldBe "Value should be positive"
    }

    test("violation should add violation") {
        context.run {
            violation("This is a violation")
        }
        context.violations.size shouldBe 1
        context.violations[0].message shouldBe "This is a violation"
    }

    test("multiple violations should be aggregated") {
        context.run {
            (-5).validate("Value should be positive") { it > 0 }
            rule("Value should be even") { 3 % 2 == 0 }
            violation("This is another violation")
        }
        context.violations.size shouldBe 3
        context.violations[0].message shouldBe "Value should be positive"
        context.violations[1].message shouldBe "Value should be even"
        context.violations[2].message shouldBe "This is another violation"
    }
})