package io.github.kverify.core.context

import io.github.kverify.core.context.Contexts.failContext
import io.github.kverify.core.context.Contexts.successContext
import io.github.kverify.dsl.model.withName
import io.kotest.core.spec.style.FunSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll

class NamedComparableContextTest :
    FunSpec({
        test("equalTo") {
            val string = "test"

            checkAll(Arb.string()) { value ->
                successContext.run {
                    value.withName("test").equalTo(value)
                }
            }

            checkAll(Arb.string().filter { it != string }) { value ->
                failContext.run {
                    value.withName("test").equalTo(string)
                }
            }
        }

        test("notEqualTo") {
            val string = "test"

            checkAll(Arb.string().filter { it != string }) { value ->
                successContext.run {
                    value.withName("test").notEqualTo(string)
                }
            }

            checkAll(Arb.string()) { value ->
                failContext.run {
                    value.withName("test").notEqualTo(value)
                }
            }
        }

        test("greaterThan") {
            val number = 5

            checkAll(Arb.int(min = number + 1)) { value ->
                successContext.run {
                    value.withName("test").greaterThan(number)
                }
            }

            checkAll(Arb.int(max = number)) { value ->
                failContext.run {
                    value.withName("test").greaterThan(number)
                }
            }
        }

        test("greaterThanOrEqualTo") {
            val number = 5

            checkAll(Arb.int(min = number)) { value ->
                successContext.run {
                    value.withName("test").greaterThanOrEqualTo(number)
                }
            }

            checkAll(Arb.int(max = number - 1)) { value ->
                failContext.run {
                    value.withName("test").greaterThanOrEqualTo(number)
                }
            }
        }

        test("lessThan") {
            val number = 5

            checkAll(Arb.int(max = number - 1)) { value ->
                successContext.run {
                    value.withName("test").lessThan(number)
                }
            }

            checkAll(Arb.int(min = number)) { value ->
                failContext.run {
                    value.withName("test").lessThan(number)
                }
            }
        }

        test("lessThanOrEqualTo") {
            val number = 5

            checkAll(Arb.int(max = number)) { value ->
                successContext.run {
                    value.withName("test").lessThanOrEqualTo(number)
                }
            }

            checkAll(Arb.int(min = number + 1)) { value ->
                failContext.run {
                    value.withName("test").lessThanOrEqualTo(number)
                }
            }
        }
    })
