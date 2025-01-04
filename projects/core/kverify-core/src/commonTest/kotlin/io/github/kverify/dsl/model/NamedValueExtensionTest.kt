package io.github.kverify.dsl.model

import io.github.kverify.core.model.NamedValue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.boolean
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import kotlin.test.DefaultAsserter.fail

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

        test("useValue") {
            checkAll(Arb.string(), Arb.string()) { userName, userSurname ->
                val user = User(userName, userSurname)

                user.withName("user").useValue {
                    it.name shouldBe userName
                    it.surname shouldBe userSurname
                }
            }
        }

        test("ifNotNull") {
            checkAll(Arb.boolean()) { isNull ->
                val value = if (isNull) null else "value"
                val namedValue = NamedValue("name", value)

                if (isNull) {
                    namedValue.ifNotNull { fail("Code inside ifNotNull should not be executed if value is null") }
                    return@checkAll
                } else {
                    namedValue.ifNotNull { return@checkAll }
                }
                fail("Code inside ifNotNull should be executed if value is not null")
            }
        }

        test("unwrapOrNull") {
            checkAll(Arb.boolean()) { isNull ->
                val value = if (isNull) null else "value"
                val namedValue = NamedValue("name", value)

                namedValue.unwrapOrNull() shouldBe if (isNull) null else namedValue
            }
        }
    })

private data class User(
    val name: String,
    val surname: String,
)
