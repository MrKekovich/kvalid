package io.github.kverify.rule

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.filter
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll

class StringRulesTest :
    FunSpec({

        test("ofLength must return true") {
            val length = 10
            checkAll(Arb.string(length..length)) { str ->
                StringRules.ofLength(length).invoke(str) shouldBe true
            }
        }

        test("ofLength must return false") {
            val length = 10
            checkAll(Arb.string(0..<length)) { str ->
                StringRules.ofLength(length).invoke(str) shouldBe false
            }
        }

        test("notOfLength must return true") {
            val length = 10
            checkAll(Arb.string(0..<length)) { str ->
                StringRules.notOfLength(length).invoke(str) shouldBe true
            }
        }

        test("maxLength must return true") {
            val maxLength = 10
            checkAll(Arb.string(0..maxLength)) { str ->
                StringRules.maxLength(maxLength).invoke(str) shouldBe true
            }
        }

        test("minLength must return true") {
            val minLength = 5
            val maxLength = minLength + 3
            checkAll(Arb.string(minLength..maxLength)) { str ->
                StringRules.minLength(minLength).invoke(str) shouldBe true
            }
        }

        test("lengthBetween must return true") {
            val lengthRange = 5..10
            checkAll(Arb.string(lengthRange)) { str ->
                StringRules.lengthBetween(lengthRange).invoke(str) shouldBe true
            }
        }

        test("lengthNotBetween must return true") {
            val lengthRange = 1..4
            val actualRange = 5..10
            checkAll(Arb.string(actualRange)) { str ->
                StringRules.lengthNotBetween(lengthRange).invoke(str) shouldBe true
            }
        }

        test("contains must return true") {
            val subStr = "t"
            checkAll(Arb.string().filter { it.contains(subStr) }) { str ->
                StringRules.contains(subStr).invoke(str) shouldBe true
            }
        }

        test("notContains must return true") {
            val subStr = "test"
            checkAll(Arb.string().filter { !it.contains(subStr) }) { str ->
                StringRules.notContains(subStr).invoke(str) shouldBe true
            }
        }

        test("startsWith must return true") {
            val prefix = "p"
            checkAll(Arb.string().filter { it.startsWith(prefix) }) { str ->
                StringRules.startsWith(prefix).invoke(str) shouldBe true
            }
        }

        test("endsWith must return true") {
            val suffix = "s"
            checkAll(Arb.string().filter { it.endsWith(suffix) }) { str ->
                StringRules.endsWith(suffix).invoke(str) shouldBe true
            }
        }

        test("alphabetic must return true") {
            checkAll(Arb.string().filter { it.matches(Regex("[a-zA-Z]+")) }) { str ->
                StringRules.alphabetic().invoke(str) shouldBe true
            }
        }

        test("alphanumeric must return true") {
            checkAll(Arb.string().filter { it.matches(Regex("[a-zA-Z0-9]+")) }) { str ->
                StringRules.alphanumeric().invoke(str) shouldBe true
            }
        }
    })
