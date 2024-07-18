package io.github.mrkekovich.kvalid.dsl

import io.github.mrkekovich.kvalid.core.model.NamedValue
import io.github.mrkekovich.kvalid.core.model.ValidationResult
import io.github.mrkekovich.kvalid.core.model.errorsOrEmpty
import io.github.mrkekovich.kvalid.core.model.isValid
import io.github.mrkekovich.kvalid.core.exception.ValidationException
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll

class ExtensionFunctionsTest : FunSpec({

    test("withName extension function") {
        checkAll(Arb.string(), Arb.int()) { name, value ->
            val namedValue = value withName name
            namedValue.name shouldBe name
            namedValue.value shouldBe value
        }
    }

    test("withValue extension function") {
        checkAll(Arb.string(), Arb.int()) { name, value ->
            val namedValue = name withValue value
            namedValue.name shouldBe name
            namedValue.value shouldBe value
        }
    }

    test("nested extension function") {
        val original = NamedValue("test", mutableListOf(1, 2, 3))
        val result = original.nested {
            add(4)
            add(5)
        }

        result shouldBe original
        result.value shouldBe mutableListOf(1, 2, 3, 4, 5)
    }

    test("KProperty.toNamed extension function") {
        val property = object {
            val testProp: String = "value"
        }::testProp

        val namedValue = property.toNamed("test value")
        namedValue.name shouldBe "testProp"
        namedValue.value shouldBe "test value"
    }

    test("Sequence<ValidationException>.toValidationResult extension function") {
        val emptySequence = emptySequence<ValidationException>()
        emptySequence.toValidationResult() shouldBe ValidationResult.valid()

        val nonEmptySequence = sequenceOf(
            ValidationException("Error 1"),
            ValidationException("Error 2")
        )
        val result = nonEmptySequence.toValidationResult()
        result.isValid shouldBe false
        result.errorsOrEmpty().size shouldBe 2
        result.errorsOrEmpty()[0].message shouldBe "Error 1"
        result.errorsOrEmpty()[1].message shouldBe "Error 2"
    }
})