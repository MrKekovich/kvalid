package org.kvalid.dsl.extension

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import org.kvalid.core.model.NamedValue
import org.kvalid.dsl.model.nested
import org.kvalid.dsl.model.withName
import org.kvalid.dsl.model.withValue

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
