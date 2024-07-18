package io.github.mrkekovich.kvalid.dsl.validator

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.test.fail

internal class AggregateTest : FunSpec({
    test("validateAll") {
        val result = validateAll {
            "".validate("fail") { it.isNotBlank() }
            rule("success") { true }
            rule("fail") { false }
            violation("fail")
        }
            .onValid { fail("should not be valid") }

        result.violations.size shouldBe 3

        result.violations.forEach {
            it.message shouldBe "fail"
        }
    }
})