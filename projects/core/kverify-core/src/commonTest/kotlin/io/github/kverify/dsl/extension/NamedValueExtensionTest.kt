package io.github.kverify.dsl.extension

import io.github.kverify.core.model.NamedValue
import io.github.kverify.dsl.model.nested
import io.github.kverify.dsl.model.withName
import io.github.kverify.dsl.model.withValue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll

class NamedValueExtensionTest :
    FunSpec({
        test("withName") {
            checkAll(Arb.string(), Arb.string()) { name, value ->
                value withName name shouldBe NamedValue(name, value)
            }
        }

        test("withValue") {
            checkAll(Arb.string(), Arb.string()) { name, value ->
                name withValue value shouldBe NamedValue(name, value)
            }
        }

        test("nested") {
            checkAll(Arb.string(), Arb.string()) { userName, userSurname ->
                val user = User(userName, userSurname)

                user.withName("user").nested {
                    name shouldBe userName
                    surname shouldBe userSurname
                }
            }
        }
    })

private data class User(
    val name: String,
    val surname: String,
)
