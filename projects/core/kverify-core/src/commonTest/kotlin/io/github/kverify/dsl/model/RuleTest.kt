package io.github.kverify.dsl.model

import io.github.kverify.core.context.Contexts.failContext
import io.github.kverify.core.context.Contexts.successContext
import io.github.kverify.dsl.extension.validate
import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class RuleTest :
    FunSpec({
        test("createUnitRule") {
            val message = "test"
            val rule = createUnitRule(message) { true }

            successContext.run { applyUnitRule(rule) }

            shouldFail {
                failContext.run { applyUnitRule(rule) }
            }.message shouldBe message
        }

        test("createUnitRule<T>(predicate)") {
            val message = "test"
            val rule =
                createRule<String> {
                    validate(message) { true }
                }

            successContext.run { "".applyRules(rule) }

            shouldFail {
                failContext.run { "".applyRules(rule) }
            }.message shouldBe message
        }

        test("createUnitRule<T>(message, predicate)") {
            val message = "test"
            val rule = createRule<String>(message) { true }

            successContext.run { "".applyRules(rule) }

            shouldFail {
                failContext.run { "".applyRules(rule) }
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

            successContext.run { namedValue.applyRules(rule) }

            shouldFail {
                failContext.run { namedValue.applyRules(rule) }
            }.message shouldBe "$name=$value"
        }
    })
