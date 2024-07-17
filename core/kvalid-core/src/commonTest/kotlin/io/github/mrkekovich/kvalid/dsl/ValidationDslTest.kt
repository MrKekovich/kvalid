package io.github.mrkekovich.kvalid.dsl

import io.github.mrkekovich.kvalid.core.dto.ValidationResult
import io.github.mrkekovich.kvalid.core.dto.errorsOrEmpty
import io.github.mrkekovich.kvalid.core.strategy.ValidationStrategy
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll

class ValidationDslTest : FunSpec({

    test("validate with default strategy should aggregate all violations") {
        val result = validate {
            rule("First rule") { false }
            rule("Second rule") { false }
        }
        result.errorsOrEmpty().size shouldBe 2
    }

    test("validate with immediate strategy should return only the first violation") {
        val result = validate(ValidationStrategy.Immediate) {
            rule("First rule") { false }
            rule("Second rule") { false }
        }
        result.errorsOrEmpty().size shouldBe 1
    }

    test("aggregateValidate should return valid result when all rules pass") {
        val result = aggregateValidate {
            rule("First rule") { true }
        }
        result shouldBe ValidationResult.Valid
    }

    test("aggregateValidate should return all violations when rules fail") {
        val result = aggregateValidate {
            rule("First rule") { false }
            rule("Second rule") { false }
        }
        result.errorsOrEmpty().size shouldBe 2
    }

    test("immediateValidate should return valid result when all rules pass") {
        val result = immediateValidate {
            rule("First rule") { true }
        }
        result shouldBe ValidationResult.Valid
    }

    test("immediateValidate should return only the first violation") {
        val result = immediateValidate {
            rule("First rule") { false }
            rule("Second rule") { false }
        }
        result.errorsOrEmpty().size shouldBe 1
    }

    test("withName") {
        checkAll(
            Arb.string(),
            Arb.int()
        ) { name, value ->
            val namedValue = value withName name
            namedValue.name shouldBe name
            namedValue.value shouldBe value
        }
    }
})
