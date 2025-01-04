package io.github.kverify.dsl.model

import io.github.kverify.core.context.Contexts.failContext
import io.github.kverify.core.context.Contexts.successContext
import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class RuleTest :
    FunSpec({
        test("createUnitRule") {
            val message = "test"
            val rule = createUnitRule(message) { true }

            successContext.run { validate(rule) }

            shouldFail {
                failContext.run { validate(rule) }
            }.message shouldBe message
        }

        test("createUnitRule<T>(predicate)") {
            val message = "test"
            val rule =
                createRule<String> {
                    validate(message) { true }
                }

            successContext.run { "".validate(rule) }

            shouldFail {
                failContext.run { "".validate(rule) }
            }.message shouldBe message
        }

        test("createUnitRule<T>(message, predicate)") {
            val message = "test"
            val rule = createRule<String>(message) { true }

            successContext.run { "".validate(rule) }

            shouldFail {
                failContext.run { "".validate(rule) }
            }.message shouldBe message
        }

        test("createNamedRule") {
            val name = "test"
            val value = "test"
            val namedValue = name withValue value

            val rule =
                createNamedRule<String> {
                    validate("${it.name}=${it.value}") { true }
                }

            successContext.run { namedValue.validate(rule) }

            shouldFail {
                failContext.run { namedValue.validate(rule) }
            }.message shouldBe "$name=$value"
        }
    })
