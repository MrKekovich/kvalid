package io.github.kverify.dsl.extension

import io.github.kverify.core.exception.ValidationException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ValidationExceptionExtTest :
    FunSpec({
        test("throwIfPresent") {
            val message = "Error"
            shouldThrow<ValidationException> {
                ValidationException(message).throwIfPresent()
            }.message shouldBe message

            (null as? ValidationException).throwIfPresent()
        }
    })
