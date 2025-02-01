package io.github.kverify.dsl.extension

import io.github.kverify.core.jvm.exception.PropertyAccessException
import io.github.kverify.core.jvm.model.toNamed
import io.github.kverify.core.model.NamedValue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class KPropertyExtensionTestJvm :
    FunSpec({
        test("toNamed") {
            val instance = ClassJvm("test")

            shouldThrow<PropertyAccessException> {
                ClassJvm::prop.toNamed()
            }

            instance.run {
                val named = ::prop.toNamed()

                named shouldBe NamedValue(::prop.name, prop)
            }
        }
    })

private data class ClassJvm(
    val prop: String,
)
