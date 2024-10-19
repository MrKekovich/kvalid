package io.github.kverify.core.context

import io.github.kverify.core.context.Contexts.failContext
import io.github.kverify.core.context.Contexts.successContext
import io.github.kverify.dsl.extension.violation
import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec

class ValidationContextTest :
    FunSpec({
        test("violation") {
            shouldFail {
                successContext.run {
                    violation("test")
                }
            }

            failContext.run {
                violation("test")
            }
        }

        test("rule") {
            successContext.run {
                validate("test") { true }
                shouldFail {
                    validate("fail") { false }
                }
            }

            failContext.run {
                validate("test") { false }
                shouldFail {
                    validate("fail") { true }
                }
            }
        }
    })
