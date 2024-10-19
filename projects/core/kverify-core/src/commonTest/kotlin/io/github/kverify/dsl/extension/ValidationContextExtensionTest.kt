package io.github.kverify.dsl.extension

import io.github.kverify.core.context.Contexts.failContext
import io.github.kverify.core.context.Contexts.successContext
import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ValidationContextExtensionTest :
    FunSpec({
        test("violation") {
            val message = "fail"
            failContext.run { violation(message) }

            shouldFail {
                successContext.run { violation(message) }
            }.message shouldBe message
        }
    })
