package org.kvalid.dsl.model

import org.kvalid.core.model.NamedValue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.kvalid.dsl.model.createRule

internal class RuleTest : FunSpec({
    test("createRule") {
        val rule = createRule("test") { true }

        rule.failMessage shouldBe "test"
        rule.validate() shouldBe true
    }

    test("createRule<T>") {
        val callback = createRule<String>("test") { it.isNotBlank() }
        val value = "test"
        val rule = callback(value)

        rule.failMessage shouldBe "test"
        rule.validate() shouldBe true
    }

    test("createRule(MessageCallback)") {
        val callback = createRule<String>(
            message = { "${it.name} must not be blank" },
            predicate = { it.isNotBlank() }
        )
        val namedValue = NamedValue("name", "test")
        val rule = callback(namedValue)

        rule.failMessage shouldBe "name must not be blank"
        rule.validate() shouldBe true
    }
})