package io.github.kverify.core.context

import io.github.kverify.core.context.Contexts.successContext
import io.github.kverify.core.model.NamedValue
import io.github.kverify.dsl.model.withName
import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ValidationContextTest :
    FunSpec({
        test("validation with message as lambda") {
            val fieldName = "test"
            val namedValue = "".withName(fieldName)

            val message: (NamedValue<String>) -> String = { "$it must not be blank" }

            val error =
                shouldFail {
                    successContext.run {
                        namedValue.validate(
                            message = message,
                            predicate = { it.isNotBlank() },
                        )
                    }
                }

            error.message shouldBe message(namedValue)
        }
    })
