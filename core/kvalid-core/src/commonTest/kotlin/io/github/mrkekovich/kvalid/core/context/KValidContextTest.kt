package io.github.mrkekovich.kvalid.core.context

import io.github.mrkekovich.kvalid.core.context.Contexts.failContext
import io.github.mrkekovich.kvalid.core.context.Contexts.successContext
import io.github.mrkekovich.kvalid.core.exception.ValidationException
import io.kotest.assertions.shouldFail
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec

internal class KValidContextTest : FunSpec({
    test("violation") {
        shouldFail {
            successContext.run {
                violation("test")
            }
        }

        shouldThrow<ValidationException> {
            failContext.run {
                violation("test")
            }
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