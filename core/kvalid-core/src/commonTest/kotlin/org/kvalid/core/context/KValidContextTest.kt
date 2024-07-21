package org.kvalid.core.context

import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec
import org.kvalid.core.context.Contexts.failContext
import org.kvalid.core.context.Contexts.successContext

internal class KValidContextTest : FunSpec({
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