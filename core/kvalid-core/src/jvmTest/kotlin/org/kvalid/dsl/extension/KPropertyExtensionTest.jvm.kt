package org.kvalid.dsl.extension

import org.kvalid.core.model.NamedValue
import org.kvalid.dsl.exception.PropertyAccessException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class KPropertyExtensionTestJvm : FunSpec({
    test("toNamed") {
        val instance = ClassJvm("test")

        shouldThrow<PropertyAccessException> {
            ClassJvm::prop.toNamed()
        }

        instance.run {
            val named = try {
                ::prop.toNamed()
            } catch (e: Throwable) {
                println(e)
            }

            println(named)

            named shouldBe NamedValue("prop", prop)
        }
    }
})

private data class ClassJvm(val prop: String)
