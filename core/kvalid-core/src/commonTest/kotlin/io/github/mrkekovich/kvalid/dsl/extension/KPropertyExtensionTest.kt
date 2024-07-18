package io.github.mrkekovich.kvalid.dsl.extension

import io.github.mrkekovich.kvalid.core.model.NamedValue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class KPropertyExtensionTest : FunSpec({
    test("toNamed(value)") {
        val instance = Class("test")

        instance.run {
            val named = ::prop.toNamed(prop)

            named shouldBe NamedValue("prop", prop)
        }
    }
})

private data class Class(val prop: String)
