package org.kvalid.core.context

import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.kvalid.core.context.Contexts.successContext
import org.kvalid.dsl.model.withName

internal class ValidationContextTest : FunSpec({
    test("validation with message as lambda") {
        val error = shouldFail {
            successContext.run {
                "".withName("test").validate(
                    message = { "${it.name} must not be blank" },
                    predicate = { it.isNotBlank() }
                )
            }
        }

        error.message shouldBe "Rule failed: test must not be blank"
    }
})