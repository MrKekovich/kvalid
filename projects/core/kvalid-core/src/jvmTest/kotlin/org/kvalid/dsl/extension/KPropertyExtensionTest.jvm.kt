package org.kvalid.dsl.extension

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.kvalid.core.model.NamedValue
import org.kvalid.dsl.exception.PropertyAccessException

class KPropertyExtensionTestJvm : FunSpec({
    test("toNamed") {
        val instance = ClassJvm("test")

        shouldThrow<PropertyAccessException> {
            ClassJvm::prop.toNamed()
        }

        instance.run {
            val named = ::prop.toNamed()

            named shouldBe NamedValue("prop", prop)
        }
    }
})

private data class ClassJvm(val prop: String)
