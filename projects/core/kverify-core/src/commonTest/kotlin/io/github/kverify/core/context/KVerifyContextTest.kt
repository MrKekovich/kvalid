package io.github.kverify.core.context

import io.github.kverify.core.context.Contexts.failContext
import io.github.kverify.core.context.Contexts.successContext
import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec

class KVerifyContextTest :
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
                rule("test") { true }
                shouldFail {
                    rule("fail") { false }
                }
            }

            failContext.run {
                rule("test") { false }
                shouldFail {
                    rule("fail") { true }
                }
            }
        }
    })
