package io.github.kverify.rule

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll

class ComparableRulesTest :
    FunSpec({
        test("equalTo must return true") {
            checkAll(Arb.int()) { num ->
                ComparableRules.equalTo(num).invoke(num) shouldBe true
            }
        }

        test("equalTo must return false") {
            checkAll(Arb.int(), Arb.int()) { num, other ->
                if (num != other) {
                    ComparableRules.equalTo(other).invoke(num) shouldBe false
                }
            }
        }

        test("notEqualTo must return true") {
            checkAll(Arb.int(), Arb.int()) { num, other ->
                if (num != other) {
                    ComparableRules.notEqualTo(other).invoke(num) shouldBe true
                }
            }
        }

        test("notEqualTo must return false") {
            checkAll(Arb.int()) { num ->
                ComparableRules.notEqualTo(num).invoke(num) shouldBe false
            }
        }

        test("greaterThan must return true") {
            checkAll(Arb.int()) { num ->
                ComparableRules.greaterThan(num - 1).invoke(num) shouldBe true
            }
        }

        test("greaterThan must return false") {
            checkAll(Arb.int()) { num ->
                ComparableRules.greaterThan(num + 1).invoke(num) shouldBe false
            }
        }

        test("greaterThanOrEqualTo must return true") {
            checkAll(Arb.int()) { num ->
                ComparableRules.greaterThanOrEqualTo(num).invoke(num) shouldBe true
            }
        }

        test("lessThan must return true") {
            checkAll(Arb.int()) { num ->
                ComparableRules.lessThan(num + 1).invoke(num) shouldBe true
            }
        }

        test("lessThan must return false") {
            checkAll(Arb.int()) { num ->
                ComparableRules.lessThan(num - 1).invoke(num) shouldBe false
            }
        }

        test("lessThanOrEqualTo must return true") {
            checkAll(Arb.int()) { num ->
                ComparableRules.lessThanOrEqualTo(num).invoke(num) shouldBe true
            }
        }

        test("between inclusive must return true") {
            checkAll(Arb.int()) { num ->
                ComparableRules.between(num - 1, num + 1).invoke(num) shouldBe true
            }
        }

        test("between exclusive must return false") {
            checkAll(Arb.int()) { num ->
                ComparableRules.between(num, num + 2, inclusive = false).invoke(num) shouldBe false
            }
        }

        test("notBetween inclusive must return false") {
            checkAll(Arb.int()) { num ->
                ComparableRules.notBetween(num - 1, num + 1).invoke(num) shouldBe false
            }
        }

        test("notBetween exclusive must return true") {
            checkAll(Arb.int()) { num ->
                ComparableRules.notBetween(num, num + 2, inclusive = false).invoke(num) shouldBe true
            }
        }
    })
