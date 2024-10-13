package org.kvalid.core.context

import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.kvalid.core.context.Contexts.successContext
import org.kvalid.core.model.NamedValue
import org.kvalid.dsl.model.withName

internal class ValidationContextTest : FunSpec({
    test("validation with message as lambda") {
        val fieldName = "test"
        val namedValue = "".withName(fieldName)

        val message: (NamedValue<String>) -> String = { "$it must not be blank" }

        val error = shouldFail {
            successContext.run {
                namedValue.validate(
                    message = message,
                    predicate = { it.isNotBlank() }
                )
            }
        }

        error.message shouldBe message(namedValue)
    }
})